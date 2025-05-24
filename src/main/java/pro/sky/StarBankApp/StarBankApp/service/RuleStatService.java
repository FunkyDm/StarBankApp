package pro.sky.StarBankApp.StarBankApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.StarBankApp.StarBankApp.dto.RuleStatDTO;
import pro.sky.StarBankApp.StarBankApp.model.ruleStat.RuleStat;
import pro.sky.StarBankApp.StarBankApp.repository.RuleStatRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RuleStatService {

    private final RuleStatRepository ruleStatRepository;

    @Autowired
    public RuleStatService(RuleStatRepository ruleStatRepository) {
        this.ruleStatRepository = ruleStatRepository;
    }

    @Transactional
    public void incrementStat(UUID ruleId) {
        RuleStat ruleStat = ruleStatRepository.findByRuleId(ruleId)
                .orElseGet(() -> new RuleStat(ruleId));
        ruleStat.incrementCount();
        ruleStatRepository.save(ruleStat);
    }

    public List<RuleStatDTO> getAllStats() {
        return ruleStatRepository.findAll()
                .stream()
                .map(ruleStat -> new RuleStatDTO(ruleStat.getRuleId(), ruleStat.getCount()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void resetAllStats() {
        List<RuleStat> stats = ruleStatRepository.findAll();
        stats.forEach(RuleStat::resetCount);
        ruleStatRepository.saveAll(stats);
    }

}
