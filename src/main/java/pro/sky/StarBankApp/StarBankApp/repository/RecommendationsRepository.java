package pro.sky.StarBankApp.StarBankApp.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class RecommendationsRepository {

    private final JdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getRandomTransactionAmount(UUID userId, String productType){
        Integer result = jdbcTemplate.queryForObject(
                "SELECT COUNT(t.AMOUNT) " +
                        "FROM transactions t " +
                        "JOIN products p ON t.product_id = p.id " +
                        "WHERE t.user_id = ? " +
                        "  AND p.type = ?;",
                Integer.class,
                userId,
                productType);
        return result != null ? result : 0;
    }

    public long getTransactionAmount(UUID userId, String productType, String transactionType) {
        Long result = jdbcTemplate.queryForObject(
                "SELECT SUM(t.AMOUNT) " +
                        "FROM transactions t " +
                        "JOIN products p ON t.product_id = p.id " +
                        "WHERE t.user_id = ? " +
                        "AND p.type = ? " +
                        "AND t.type = ?;",
                Long.class,
                userId,
                productType,
                transactionType);
        return result != null ? result : 0L;
    }

    public int getTransactionCount(UUID userId, String credit) {
        return 0;
    }
}
