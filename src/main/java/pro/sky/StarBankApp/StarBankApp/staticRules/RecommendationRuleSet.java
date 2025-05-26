package pro.sky.StarBankApp.StarBankApp.staticRules;

import pro.sky.StarBankApp.StarBankApp.dto.Recommendation;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
    Optional<Recommendation> getRecommendation(UUID userId);
}
