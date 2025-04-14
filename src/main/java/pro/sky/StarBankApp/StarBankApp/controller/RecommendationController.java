package pro.sky.StarBankApp.StarBankApp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.StarBankApp.StarBankApp.model.RecommendationDTO;
import pro.sky.StarBankApp.StarBankApp.service.RecommendationService;

import java.util.UUID;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<RecommendationDTO> getRecommendations(@PathVariable UUID userId) {
        RecommendationDTO response = recommendationService.getRecommendations(userId);
        return ResponseEntity.ok(response);
    }
}
