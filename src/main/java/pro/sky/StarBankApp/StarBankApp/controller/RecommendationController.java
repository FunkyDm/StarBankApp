package pro.sky.StarBankApp.StarBankApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.StarBankApp.StarBankApp.dto.Recommendation;

import java.util.UUID;

@RestController
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService){
        this.recommendationService = recommendationService;
    }

    @GetMapping("/recommendation/{user_id}")
    public Recommendation getRecommendations(@PathVariable("user_id")UUID userId){
        return recommendationService.getRecommendations(userId.toString());
    }
}