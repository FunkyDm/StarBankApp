package pro.sky.StarBankApp.StarBankApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.StarBankApp.StarBankApp.model.DynamicRule;

import java.util.UUID;

public interface DynamicRuleRepository extends JpaRepository<DynamicRule, UUID> {
}
