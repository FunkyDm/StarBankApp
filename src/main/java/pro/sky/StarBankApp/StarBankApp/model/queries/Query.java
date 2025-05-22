package pro.sky.StarBankApp.StarBankApp.model.queries;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.spind30.starbankapp.model.enums.QueryType;
import com.github.spind30.starbankapp.model.rule.DynamicRule;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "queries")
@ToString(exclude = "dynamicRule")
@EqualsAndHashCode(exclude = "dynamicRule")
public class Query {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @Enumerated(EnumType.STRING)
    @JsonProperty ("query")
    private QueryType queryType;

    private List<String> arguments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dynamic_rule_id")
    @JsonIgnore
    private DynamicRule dynamicRule;

    private boolean negate;

}


