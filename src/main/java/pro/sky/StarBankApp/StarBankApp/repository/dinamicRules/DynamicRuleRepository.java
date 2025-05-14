package pro.sky.StarBankApp.StarBankApp.repository.dinamicRules;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.StarBankApp.StarBankApp.model.DynamicRule;

@Profile("!disabledModule")
@Repository
public interface DynamicRuleRepository extends JpaRepository<DynamicRule, Long> {
}
