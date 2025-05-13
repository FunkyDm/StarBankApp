package pro.sky.StarBankApp.StarBankApp.model.dinamicRules;

import jakarta.persistence.*;
import org.springframework.context.annotation.Profile;

@Profile("!disabledModule")
@Entity
@Table(name = "dynamic_rules")
public class DynamicRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "product_text")
    private String productText;

    @Column(name = "rule_json", columnDefinition = "TEXT")
    private String ruleJson;

    public DynamicRule() {
    }

    public DynamicRule(Long id, String productName, String productId, String productText, String ruleJson) {
        this.id = id;
        this.productName = productName;
        this.productId = productId;
        this.productText = productText;
        this.ruleJson = ruleJson;
    }
}