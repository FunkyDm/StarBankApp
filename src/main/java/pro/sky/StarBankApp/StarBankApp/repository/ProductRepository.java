package pro.sky.StarBankApp.StarBankApp.repository;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import pro.sky.StarBankApp.StarBankApp.model.ProductRecommendation;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;
    private final Cache<CacheKey, Boolean> userProductCache;
    private final Cache<CacheKey, Boolean> activeUserCache;
    private final Cache<CacheKey, BigDecimal> sumCache;

    public ProductRepository(@Qualifier("jdbcTemplateH2") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.userProductCache = Caffeine.newBuilder()
                .maximumSize(10_000)
                .expireAfterWrite(1, TimeUnit.HOURS)
                .build();
        this.activeUserCache = Caffeine.newBuilder()
                .maximumSize(10_000)
                .expireAfterWrite(1, TimeUnit.HOURS)
                .build();
        this.sumCache = Caffeine.newBuilder()
                .maximumSize(10_000)
                .expireAfterWrite(1, TimeUnit.HOURS)
                .build();
    }

    public ProductRecommendation findProductById(UUID productId) {
        String sql = "SELECT name, id, description FROM products WHERE id = ?";
        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new ProductRecommendation(
                        rs.getString("name"),
                        UUID.fromString(rs.getString("id")),
                        rs.getString("description")
                ),
                productId.toString()
        );
    }

    public boolean isUserUsingProductType(UUID userId, String productType) {
        String sql = """
                SELECT COUNT(*) > 0 
                FROM transactions t
                JOIN products p ON t.product_id = p.id
                WHERE t.user_id = ? AND p.type = ?
                """;
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                sql, Boolean.class, userId.toString(), productType));
    }

    public BigDecimal getTotalDepositsByProductType(UUID userId, String productType) {
        String sql = """
                SELECT COALESCE(SUM(t.amount), 0)
                FROM transactions t
                JOIN products p ON t.product_id = p.id
                WHERE t.user_id = ? AND p.type = ? AND t.type = 'DEPOSIT'
                """;
        return jdbcTemplate.queryForObject(
                sql, BigDecimal.class, userId.toString(), productType);
    }

    public BigDecimal getTotalSpendsByProductType(UUID userId, String productType) {
        String sql = """
                SELECT COALESCE(SUM(t.amount), 0)
                FROM transactions t
                JOIN products p ON t.product_id = p.id
                WHERE t.user_id = ? AND p.type = ? AND t.type = 'WITHDRAW'
                """;
        return jdbcTemplate.queryForObject(
                sql, BigDecimal.class, userId.toString(), productType);
    }

    public boolean isActiveUserOfProductType(UUID userId, String productType) {
        CacheKey key = new CacheKey(userId, productType);
        return activeUserCache.get(key, k -> {
            String sql = """
                    SELECT COUNT(*) >= 5 
                    FROM transactions t
                    JOIN products p ON t.product_id = p.id
                    WHERE t.user_id = ? AND p.type = ?
                    """;
            return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                    sql, Boolean.class, userId.toString(), productType));
        });
    }

    public boolean compareTransactionSumWithConstant(UUID userId, String productType,
                                                     String transactionType, String operator,
                                                     BigDecimal value) {
        CacheKey key = new CacheKey(userId, productType, transactionType);
        BigDecimal sum = sumCache.get(key, k -> {
            String sql = """
                    SELECT COALESCE(SUM(t.amount), 0)
                    FROM transactions t
                    JOIN products p ON t.product_id = p.id
                    WHERE t.user_id = ? AND p.type = ? AND t.type = ?
                    """;
            return jdbcTemplate.queryForObject(
                    sql, BigDecimal.class, userId.toString(), productType, transactionType);
        });

        return switch (operator) {
            case ">" -> sum.compareTo(value) > 0;
            case "<" -> sum.compareTo(value) < 0;
            case "=" -> sum.compareTo(value) == 0;
            case ">=" -> sum.compareTo(value) >= 0;
            case "<=" -> sum.compareTo(value) <= 0;
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
    }

    public boolean compareDepositWithWithdraw(UUID userId, String productType, String operator) {
        CacheKey depositKey = new CacheKey(userId, productType, "DEPOSIT");
        BigDecimal depositSum = sumCache.get(depositKey, k -> getTransactionSum(userId, productType, "DEPOSIT"));

        CacheKey withdrawKey = new CacheKey(userId, productType, "WITHDRAW");
        BigDecimal withdrawSum = sumCache.get(withdrawKey, k -> getTransactionSum(userId, productType, "WITHDRAW"));

        return switch (operator) {
            case ">" -> depositSum.compareTo(withdrawSum) > 0;
            case "<" -> depositSum.compareTo(withdrawSum) < 0;
            case "=" -> depositSum.compareTo(withdrawSum) == 0;
            case ">=" -> depositSum.compareTo(withdrawSum) >= 0;
            case "<=" -> depositSum.compareTo(withdrawSum) <= 0;
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
    }

    private BigDecimal getTransactionSum(UUID userId, String productType, String transactionType) {
        String sql = """
                SELECT COALESCE(SUM(t.amount), 0)
                FROM transactions t
                JOIN products p ON t.product_id = p.id
                WHERE t.user_id = ? AND p.type = ? AND t.type = ?
                """;
        return jdbcTemplate.queryForObject(
                sql, BigDecimal.class, userId.toString(), productType, transactionType);
    }

    private record CacheKey(UUID userId, String... args) {
    }
}
