package pro.sky.StarBankApp.StarBankApp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.StarBankApp.StarBankApp.dto.RecommendationResponse;
import pro.sky.StarBankApp.StarBankApp.rules.RecommendationRuleSet;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceTest {
    @Mock
    private RecommendationRuleSet rule1, rule2;

    @Test
    void whenOneRuleApplies_thenRecommendationIsReturned() {
        //given
        String userId = "testUser";
        RecommendationResponse.Recommendation recommendation =
                new RecommendationResponse.Recommendation("Try Product X!",
                        "testId",
                        "testText");

        when(rule1.apply(userId)).thenReturn(Optional.of(recommendation));
        when(rule2.apply(userId)).thenReturn(Optional.empty());

        RecommendationService service = new RecommendationService(List.of(rule1, rule2));

        //when
        RecommendationResponse response = service.getRecommendations(userId);

        //then
        assertEquals(userId, response.getUserId());
        assertEquals(1, response.getRecommendations().size());
        assertEquals(recommendation, response.getRecommendations().get(0));
    }

    @Test
    void whenNoRulesApply_thenEmptyListIsReturned(){
        //given
        String userId = "testId";
        when(rule1.apply(userId)).thenReturn(Optional.empty());
        when(rule2.apply(userId)).thenReturn(Optional.empty());

        RecommendationService service = new RecommendationService(List.of(rule1, rule2));

        //when
        RecommendationResponse response = service.getRecommendations(userId);

        //then
        assertEquals(userId, response.getUserId());
        assertTrue(response.getRecommendations().isEmpty());
    }

    @Test
    void whenMultipleRulesApply_thenAllRecommendationsAreReturned(){
        //given
        String userId = "testId";
        RecommendationResponse.Recommendation rec1 =
                new RecommendationResponse.Recommendation(
                        "Try product X",
                        "testIdX",
                        "testTextX"
                );
        RecommendationResponse.Recommendation rec2 =
                new RecommendationResponse.Recommendation(
                        "Try product Y",
                        "testIdY",
                        "testTextY"
                );

        when(rule1.apply(userId)).thenReturn(Optional.of(rec1));
        when(rule2.apply(userId)).thenReturn(Optional.of(rec2));

        RecommendationService service = new RecommendationService(List.of(rule1, rule2));

        //when
        RecommendationResponse response = service.getRecommendations(userId);

        //then
        assertEquals(userId, response.getUserId());
        assertEquals(2, response.getRecommendations().size());
        assertTrue(response.getRecommendations().containsAll(List.of(rec1, rec2)));
    }
}