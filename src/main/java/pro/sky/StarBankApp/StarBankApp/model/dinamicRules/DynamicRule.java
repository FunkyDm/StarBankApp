package pro.sky.StarBankApp.StarBankApp.model.dinamicRules;

import jakarta.persistence.*;
// import org.springframework.data.annotation.Id;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "dynamic_rules")
public class DynamicRule {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String productName;
    private UUID productId;
    private String productText;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "rule_id")
    private List<RuleCondition> conditions = new ArrayList<>();

    public DynamicRule() {
    }

    public DynamicRule(UUID id, String productName, UUID productId, String productText, List<RuleCondition> conditions) {
        this.id = id;
        this.productName = productName;
        this.productId = productId;
        this.productText = productText;
        this.conditions = conditions;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getProductText() {
        return productText;
    }

    public void setProductText(String productText) {
        this.productText = productText;
    }

    public List<RuleCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<RuleCondition> conditions) {
        this.conditions = conditions;
    }
}
