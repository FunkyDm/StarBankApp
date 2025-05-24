package pro.sky.StarBankApp.StarBankApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.StarBankApp.StarBankApp.model.ruleStat.RuleStat;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RuleStatRepository extends JpaRepository<RuleStat, UUID> {

    Optional<RuleStat> findByRuleId(UUID ruleId);
}

