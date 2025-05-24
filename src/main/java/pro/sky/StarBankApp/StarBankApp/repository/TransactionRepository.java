package pro.sky.StarBankApp.StarBankApp.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepository {
    private final JdbcTemplate jdbcTemplate;

    public TransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean usesProductType(String userId, String productType) {
        String sql = """
                SELECT COUNT(*)
                FROM transactions t
                JOIN products p ON t.product_id = p.id
                WHERE t.user_id = ? AND p.type = ?
                """;

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                userId,
                productType);
        return count != null && count > 0;
    }

    public double getTotalDepositByType(String userId, String productType) {
        String sql = """
                SELECT COALESCE (SUM(t.amount), 0)
                FROM transactions t
                JOIN products p ON t.product_id = p.id
                WHERE t.user_id = ? AND p.type = ? AND t.type = 'DEPOSIT'
                """;
        try{
            return jdbcTemplate.queryForObject(
                    sql,
                    Double.class,
                    userId,
                    productType);
        } catch (EmptyResultDataAccessException e){
            return 0.0;
        }
    }

    public double getTotalSpendByType(String userId, String productType) {
        String sql = """
                SELECT COALESCE(SUM(t.amount),0)
                FROM transactions t
                JOIN products p ON t.product_id = p.id
                WHERE t.user_id = ? AND p.type = ? AND t.type = 'WITHDRAW'
                """;

        try{
            return jdbcTemplate.queryForObject(
                    sql,
                    Double.class,
                    userId,
                    productType);
        }catch (EmptyResultDataAccessException e){
            return 0.0;
        }

    }

}