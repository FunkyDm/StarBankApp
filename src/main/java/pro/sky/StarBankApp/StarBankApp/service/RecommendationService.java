package pro.sky.StarBankApp.StarBankApp.service;

import org.springframework.stereotype.Service;
import pro.sky.StarBankApp.StarBankApp.dto.RecommendationResponse;
import pro.sky.StarBankApp.StarBankApp.rules.RecommendationRuleSet;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecommendationService {
    private final List<RecommendationRuleSet> rules;

    public RecommendationService(List<RecommendationRuleSet> rules) {
        this.rules = rules;
    }

    public RecommendationResponse getRecommendations(String userId) {
        RecommendationResponse response = new RecommendationResponse();

        response.setUserId(userId);

        List<RecommendationResponse.Recommendation> recommendations = rules.stream()
                .map(rule -> rule.apply(userId))
                .flatMap(Optional::stream)
                .collect(Collectors.toList());

        response.setRecommendations(recommendations);
        return response;
    }
}