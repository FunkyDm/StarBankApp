package pro.sky.StarBankApp.StarBankApp.rules;

import pro.sky.StarBankApp.StarBankApp.dto.Recommendation;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
    Optional<Recommendation> getRecommendation(UUID userId);
}
