package pro.sky.StarBankApp.StarBankApp.model.rule;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dynamic_rules")
@ToString(exclude = "rule")
@EqualsAndHashCode(exclude = "rule")
@JsonPropertyOrder({"product_name", "product_id", "product_text", "rule"})
public class DynamicRule {

    @JsonProperty("product_name")
    private String productName;
    @Id
    @JsonProperty("product_id")
    private UUID productId;
    @JsonProperty("product_text")
    private String productText;

    @OneToMany(mappedBy = "dynamicRule", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Query> rule;
}




