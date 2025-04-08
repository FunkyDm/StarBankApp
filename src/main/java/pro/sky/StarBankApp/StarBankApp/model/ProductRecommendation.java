package pro.sky.StarBankApp.StarBankApp.model;

import java.util.UUID;

public record ProductRecommendation(
        String name,
        UUID id,
        String text
) {}
