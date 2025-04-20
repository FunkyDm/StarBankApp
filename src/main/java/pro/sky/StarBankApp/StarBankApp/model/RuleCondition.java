package pro.sky.StarBankApp.StarBankApp.model;

import jakarta.persistence.*;
// import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "rule_conditions")
public class RuleCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String queryType;

    @ElementCollection
    @CollectionTable(name = "rule_arguments", joinColumns = @JoinColumn(name = "condition_id"))
    @Column(name = "argument")
    private List<String> arguments = new ArrayList<>();

    private boolean negate;

    public RuleCondition() {
    }

    public RuleCondition(UUID id, String queryType, List<String> arguments, boolean negate) {
        this.id = id;
        this.queryType = queryType;
        this.arguments = arguments;
        this.negate = negate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public boolean isNegate() {
        return negate;
    }

    public void setNegate(boolean negate) {
        this.negate = negate;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }
}