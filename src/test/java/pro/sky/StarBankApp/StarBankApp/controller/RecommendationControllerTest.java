package pro.sky.StarBankApp.StarBankApp.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import pro.sky.StarBankApp.StarBankApp.model.ProductRecommendation;
import pro.sky.StarBankApp.StarBankApp.model.RecommendationDTO;
import pro.sky.StarBankApp.StarBankApp.service.RecommendationService;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecommendationControllerTest {

    @Mock
    private RecommendationService recommendationService;

    @InjectMocks
    private RecommendationController recommendationController;

    @Test
    void getRecommendations_shouldReturnOkStatus() {
        // Given
        UUID userId = UUID.randomUUID();
        String productId = "productId";
        UUID descriptionId = UUID.randomUUID();
        String text = "text";
        ProductRecommendation recommendation = new ProductRecommendation(productId, descriptionId, text);
        RecommendationDTO expectedDTO = new RecommendationDTO(userId.toString(), List.of(recommendation));

        when(recommendationService.getRecommendations(userId)).thenReturn(expectedDTO);

        // When
        ResponseEntity<RecommendationDTO> response = recommendationController.getRecommendations(userId);

        // Then
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getRecommendations_shouldReturnCorrectBody() {
        // Given
        UUID userId = UUID.randomUUID();
        String productId = "productId";
        UUID descriptionId = UUID.randomUUID();
        String text = "text";
        ProductRecommendation recommendation = new ProductRecommendation(productId, descriptionId, text);
        RecommendationDTO expectedDTO = new RecommendationDTO(userId.toString(), List.of(recommendation));

        when(recommendationService.getRecommendations(userId)).thenReturn(expectedDTO);

        // When
        ResponseEntity<RecommendationDTO> response = recommendationController.getRecommendations(userId);

        // Then
        assertEquals(expectedDTO, response.getBody());
    }

    @Test
    void getRecommendations_shouldCallServiceOnce() {
        // Given
        UUID userId = UUID.randomUUID();
        when(recommendationService.getRecommendations(userId))
                .thenReturn(new RecommendationDTO(userId.toString(), List.of()));

        // When
        recommendationController.getRecommendations(userId);

        // Then
        verify(recommendationService, times(1)).getRecommendations(userId);
    }
}