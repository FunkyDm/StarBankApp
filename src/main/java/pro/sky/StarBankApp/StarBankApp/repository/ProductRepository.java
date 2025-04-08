package pro.sky.StarBankApp.StarBankApp.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.StarBankApp.StarBankApp.model.ProductRecommendation;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
}
