package pro.sky.StarBankApp.StarBankApp;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRule {
    Optional getRecommendation(UUID userId);

    Optional<Object> check(UUID userId);
}

