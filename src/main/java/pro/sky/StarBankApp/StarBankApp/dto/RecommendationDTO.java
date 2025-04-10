package pro.sky.StarBankApp.StarBankApp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import pro.sky.StarBankApp.StarBankApp.model.Recommendation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class RecommendationDTO {
    @JsonProperty("user_id")
    UUID userId;
    List<Recommendation> recommendations = new ArrayList<>();
}
