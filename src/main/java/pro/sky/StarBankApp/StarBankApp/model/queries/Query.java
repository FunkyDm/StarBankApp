package pro.sky.StarBankApp.StarBankApp.model.queries;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import lombok.*;
import pro.sky.StarBankApp.StarBankApp.model.enums.QueryType;
import pro.sky.StarBankApp.StarBankApp.model.rule.DynamicRule;

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


