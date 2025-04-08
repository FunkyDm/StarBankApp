package pro.sky.StarBankApp.StarBankApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.StarBankApp.StarBankApp.RecommendationRule;
import pro.sky.StarBankApp.StarBankApp.model.ProductRecommendation;
import pro.sky.StarBankApp.StarBankApp.model.RecommendationDTO;
import pro.sky.StarBankApp.StarBankApp.repository.ProductRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Service
public class RecommendationService {

    private final List<RecommendationRule> rules;
    private final ProductRepository productRepository;

    @Autowired
    public RecommendationService(ProductRepository productRepository,
                                 List<RecommendationRule> rules) {
        this.productRepository = productRepository;
        this.rules = rules;
    }

    public RecommendationDTO getRecommendations(UUID userId) {
        List<ProductRecommendation> recommendations = rules.stream()
                .map(rule -> rule.check(userId))
                .filter(Objects::nonNull)
                .toList();

        return new RecommendationDTO(userId.toString(), recommendations);
    }
}