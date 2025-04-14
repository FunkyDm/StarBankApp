package pro.sky.StarBankApp.StarBankApp;

import pro.sky.StarBankApp.StarBankApp.model.ProductRecommendation;
import java.util.UUID;

public interface RecommendationRule {
    ProductRecommendation check(UUID userId);
}
