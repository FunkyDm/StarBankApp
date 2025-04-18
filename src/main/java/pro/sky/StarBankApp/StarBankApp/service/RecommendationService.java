package pro.sky.StarBankApp.StarBankApp.service;

import org.springframework.stereotype.Service;
import pro.sky.StarBankApp.StarBankApp.RecommendationRule;
import pro.sky.StarBankApp.StarBankApp.model.ProductRecommendation;
import pro.sky.StarBankApp.StarBankApp.model.RecommendationDTO;
import pro.sky.StarBankApp.StarBankApp.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Service
public class RecommendationService {
    private final ProductRepository productRepository;
    private final List<RecommendationRule> staticRules;
    private final DynamicRuleService dynamicRuleService;

    public RecommendationService(ProductRepository productRepository,
                                 List<RecommendationRule> staticRules,
                                 DynamicRuleService dynamicRuleService) {
        this.productRepository = productRepository;
        this.staticRules = staticRules;
        this.dynamicRuleService = dynamicRuleService;
    }

    public RecommendationDTO getRecommendations(UUID userId) {
        List<ProductRecommendation> recommendations = new ArrayList<>();

        // Check static rules
        recommendations.addAll(staticRules.stream()
                .map(rule -> rule.check(userId))
                .filter(Objects::nonNull)
                .toList());

        // Check dynamic rules
        recommendations.addAll(dynamicRuleService.getAllRules().stream()
                .filter(rule -> dynamicRuleService.checkRuleForUser(rule, userId, productRepository))
                .map(rule -> new ProductRecommendation(
                        rule.getProductName(),
                        rule.getProductId(),
                        rule.getProductText()
                ))
                .toList());

        return new RecommendationDTO(userId.toString(), recommendations);
    }
}