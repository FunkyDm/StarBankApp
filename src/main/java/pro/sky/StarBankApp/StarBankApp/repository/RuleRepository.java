package pro.sky.StarBankApp.StarBankApp.repository;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.StarBankApp.StarBankApp.model.rule.DynamicRule;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Repository
public interface RuleRepository extends JpaRepository<DynamicRule, Long> {
    Cache<String, Integer> ruleTriggerCountCache = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS)
            .maximumSize(100)
            .build();

    default void incrementRuleTriggerCount(UUID ruleId) {
        String cacheKey = ruleId.toString();
        Integer currentValue = ruleTriggerCountCache.getIfPresent(cacheKey);
        ruleTriggerCountCache.put(cacheKey, currentValue != null ? currentValue + 1 : 1);
    }

    default void clearCache() {
        ruleTriggerCountCache.invalidateAll();
    }

}
