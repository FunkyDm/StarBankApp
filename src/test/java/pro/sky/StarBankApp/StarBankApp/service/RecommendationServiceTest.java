package pro.sky.StarBankApp.StarBankApp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.StarBankApp.StarBankApp.RecommendationRule;
import pro.sky.StarBankApp.StarBankApp.model.ProductRecommendation;
import pro.sky.StarBankApp.StarBankApp.model.RecommendationDTO;
import pro.sky.StarBankApp.StarBankApp.repository.ProductRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private List<RecommendationRule> rules;

    @InjectMocks
    private RecommendationService recommendationService;

    @Test
    void getRecommendations_shouldReturnCorrectUserId() {
        // Given
        UUID userId = UUID.randomUUID();
        when(rules.stream()).thenReturn(Stream.empty());

        // When
        RecommendationDTO result = recommendationService.getRecommendations(userId);

        // Then
        assertEquals(userId.toString(), result.userId());
    }

    @Test
    void getRecommendations_shouldIncludeAllNonNullRecommendations() {
        // Given
        UUID userId = UUID.randomUUID();
        String productId1 = "productId1";
        UUID descriptionId1 = UUID.randomUUID();
        String text1 = "text1";
        String productId2 = "productId2";
        UUID descriptionId2 = UUID.randomUUID();
        String text2 = "text2";

        ProductRecommendation recommendation1 = new ProductRecommendation(productId1, descriptionId1, text1);
        ProductRecommendation recommendation2 = new ProductRecommendation(productId2, descriptionId2, text2);

        RecommendationRule rule1 = mock(RecommendationRule.class);
        RecommendationRule rule2 = mock(RecommendationRule.class);
        RecommendationRule rule3 = mock(RecommendationRule.class);

        when(rules.stream()).thenReturn(Stream.of(rule1, rule2, rule3));
        when(rule1.check(userId)).thenReturn(recommendation1);
        when(rule2.check(userId)).thenReturn(null);
        when(rule3.check(userId)).thenReturn(recommendation2);

        // When
        RecommendationDTO result = recommendationService.getRecommendations(userId);

        // Then
        assertEquals(2, result.recommendations().size());
        assertTrue(result.recommendations().contains(recommendation1));
        assertTrue(result.recommendations().contains(recommendation2));
    }

    @Test
    void getRecommendations_shouldReturnEmptyListWhenNoRules() {
        // Given
        UUID userId = UUID.randomUUID();
        when(rules.stream()).thenReturn(Stream.empty());

        // When
        RecommendationDTO result = recommendationService.getRecommendations(userId);

        // Then
        assertTrue(result.recommendations().isEmpty());
    }

    @Test
    void getRecommendations_shouldReturnEmptyListWhenAllRulesReturnNull() {
        // Given
        UUID userId = UUID.randomUUID();
        RecommendationRule rule1 = mock(RecommendationRule.class);
        RecommendationRule rule2 = mock(RecommendationRule.class);

        when(rules.stream()).thenReturn(Stream.of(rule1, rule2));
        when(rule1.check(userId)).thenReturn(null);
        when(rule2.check(userId)).thenReturn(null);

        // When
        RecommendationDTO result = recommendationService.getRecommendations(userId);

        // Then
        assertTrue(result.recommendations().isEmpty());
    }
}