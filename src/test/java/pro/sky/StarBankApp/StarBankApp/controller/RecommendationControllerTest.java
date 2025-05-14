package pro.sky.StarBankApp.StarBankApp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pro.sky.StarBankApp.StarBankApp.dto.RecommendationResponse;
import pro.sky.StarBankApp.StarBankApp.service.RecommendationService;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecommendationController.class)
class RecommendationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RecommendationService recommendationService;

    @Test
    void whenValidUserId_thenReturnsRecommendations() throws Exception {
        //given
        UUID userId = UUID.randomUUID();
        RecommendationResponse response = new RecommendationResponse();
        response.setUserId(userId.toString());
        response.setRecommendations(List.of(
                new RecommendationResponse.Recommendation(
                        "Try product X",
                        "testId",
                        "testText")
        ));

        when(recommendationService.getRecommendations(userId.toString()))
                .thenReturn(response);

        //when and then
        mockMvc.perform(get("/recommendation/{user_id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId.toString()))
                .andExpect(jsonPath("$.recommendations[0].name").value("Try product X"))
                .andExpect(jsonPath("$.recommendations[0].id").value("testId"))
                .andExpect(jsonPath("$.recommendations[0].text").value("testText"));
    }

    @Test
    void whenNoRecommendations_thenReturnEmptyList() throws Exception {
        //given
        UUID userId = UUID.randomUUID();
        RecommendationResponse emptyResponse = new RecommendationResponse();
        emptyResponse.setUserId(userId.toString());
        emptyResponse.setRecommendations(List.of());

        when(recommendationService.getRecommendations(userId.toString()))
                .thenReturn(emptyResponse);

        //when and then
        mockMvc.perform(get("/recommendation/{user_id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId.toString()))
                .andExpect(jsonPath("$.recommendations").isEmpty());
    }

    @Test
    void whenInvalidUserId_thenReturnsBadRequest() throws Exception {
        //given
        String invalidUserId = "not-a-UUID";

        //when and then
        mockMvc.perform(get("/recommendation/{user_id}", invalidUserId))
                .andExpect(status().isBadRequest());
    }

}