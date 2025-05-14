package pro.sky.StarBankApp.StarBankApp.rules;

import pro.sky.StarBankApp.StarBankApp.dto.RecommendationResponse;

import java.util.Optional;

public interface RecommendationRuleSet {
    Optional<RecommendationResponse.Recommendation> apply(String userId);
}