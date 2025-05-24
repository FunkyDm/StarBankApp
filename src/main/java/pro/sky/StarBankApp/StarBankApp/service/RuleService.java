package pro.sky.StarBankApp.StarBankApp.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.StarBankApp.StarBankApp.dto.DynamicRuleDTO;
import pro.sky.StarBankApp.StarBankApp.model.rule.DynamicRule;
import pro.sky.StarBankApp.StarBankApp.repository.RuleRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RuleService {
    private static final Logger logger = LoggerFactory.getLogger(RuleService.class);

    private final RuleRepository ruleRepository;

    @Transactional
    public DynamicRuleDTO createRule(DynamicRuleDTO dynamicRuleDTO) {
        DynamicRule dynamicRule = dynamicRuleDTO.toEntity();
        if (dynamicRule.getRule() != null) {
            dynamicRule.getRule().forEach(query -> query.setDynamicRule(dynamicRule));
        }
        DynamicRule savedRule = ruleRepository.save(dynamicRule);
        logger.info("Правило сохранено: {}", savedRule.getProductId());
        return DynamicRuleDTO.fromEntity(savedRule);
    }

    public void deleteRule(UUID id) {
        if (ruleRepository.existsById(id)) {
            ruleRepository.deleteById(id);
            logger.info("Правило удалено: {}", id);
        } else {
            logger.warn("Правило с ID {} не найдено для удаления.", id);
        }
    }


    @Transactional
    public DynamicRuleDTO editRule(DynamicRuleDTO dynamicRuleDTO) {
        DynamicRule updatedRule = ruleRepository.save(dynamicRuleDTO.toEntity());
        logger.info("Правило обновлено: {}", updatedRule.getProductId());
        return DynamicRuleDTO.fromEntity(updatedRule);
    }

    public List<DynamicRuleDTO> getAllRules() {
        return ruleRepository.findAll()
                .stream()
                .map(DynamicRuleDTO::fromEntity)
                .collect(Collectors.toList());
    }

}
