package pro.sky.StarBankApp.StarBankApp.repository.dinamicRules;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.StarBankApp.StarBankApp.model.rule.DynamicRule;

import java.util.UUID;

public interface RuleRepository extends JpaRepository<DynamicRule, UUID> {
}
