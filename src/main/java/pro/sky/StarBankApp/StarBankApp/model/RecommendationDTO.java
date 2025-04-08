package pro.sky.StarBankApp.StarBankApp.model;

import java.util.List;

public record RecommendationDTO(
        String userId,
        List<ProductRecommendation> recommendations
) {}
