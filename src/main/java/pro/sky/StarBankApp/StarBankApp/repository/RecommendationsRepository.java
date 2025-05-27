package pro.sky.StarBankApp.StarBankApp.repository;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Repository
public class RecommendationsRepository {

    private final JdbcTemplate jdbcTemplate;


    private final Cache<String, Integer> transactionCountCache = Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .maximumSize(100)
            .build();


    private final Cache<String, Long> transactionAmountCache = Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .maximumSize(100)
            .build();

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public int getTransactionCount(UUID userId, String productType) {
        String cacheKey = userId + ":" + productType;
        Integer cachedResult = transactionCountCache.getIfPresent(cacheKey);
        if (cachedResult != null) {
            return cachedResult;
        }

        Integer result = jdbcTemplate.queryForObject(
                "SELECT COUNT(t.AMOUNT) " +
                        "FROM transactions t " +
                        "JOIN products p ON t.product_id = p.id " +
                        "WHERE t.user_id = ? AND p.type = ?;",
                Integer.class,
                userId,
                productType
        );


        if (result != null) {
            transactionCountCache.put(cacheKey, result);
        }
        return result != null ? result : 0;
    }


    public long getTransactionAmount(UUID userId, String productType, String transactionType) {
        String cacheKey = userId + ":" + productType + ":" + transactionType;
        Long cachedResult = transactionAmountCache.getIfPresent(cacheKey);
        if (cachedResult != null) {
            return cachedResult;
        }

        Long result = jdbcTemplate.queryForObject(
                "SELECT SUM(t.AMOUNT) " +
                        "FROM transactions t " +
                        "JOIN products p ON t.product_id = p.id " +
                        "WHERE t.user_id = ? AND p.type = ? AND t.type = ?;",
                Long.class,
                userId,
                productType,
                transactionType
        );

        if (result != null) {
            transactionAmountCache.put(cacheKey, result);
        }
        return result != null ? result : 0L;
    }

}