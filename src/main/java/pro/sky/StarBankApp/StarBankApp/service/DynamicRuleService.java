package pro.sky.StarBankApp.StarBankApp.service;

import org.springframework.stereotype.Service;
import pro.sky.StarBankApp.StarBankApp.model.DynamicRule;
import pro.sky.StarBankApp.StarBankApp.model.RuleCondition;
import pro.sky.StarBankApp.StarBankApp.repository.DynamicRuleRepository;
import pro.sky.StarBankApp.StarBankApp.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class DynamicRuleService {
    private final DynamicRuleRepository ruleRepository;

    public DynamicRuleService(DynamicRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    public DynamicRule createRule(DynamicRule rule) {
        return ruleRepository.save(rule);
    }

    public List<DynamicRule> getAllRules() {
        return ruleRepository.findAll();
    }

    public void deleteRule(UUID ruleId) {
        ruleRepository.deleteById(ruleId);
    }

    public boolean checkRuleForUser(DynamicRule rule, UUID userId, ProductRepository productRepo) {
        for (RuleCondition condition : rule.getConditions()) {
            boolean result = evaluateCondition(condition, userId, productRepo);
            if (condition.isNegate()) {
                result = !result;
            }
            if (!result) {
                return false;
            }
        }
        return true;
    }

    private boolean evaluateCondition(RuleCondition condition, UUID userId, ProductRepository productRepo) {
        switch (condition.getQueryType()) {
            case "USER_OF":
                return productRepo.isUserUsingProductType(userId, condition.getArguments().get(0));
            case "ACTIVE_USER_OF":
                return productRepo.isActiveUserOfProductType(userId, condition.getArguments().get(0));
            case "TRANSACTION_SUM_COMPARE":
                return productRepo.compareTransactionSumWithConstant(
                        userId,
                        condition.getArguments().get(0), // product type
                        condition.getArguments().get(1), // transaction type
                        condition.getArguments().get(2), // operator
                        new BigDecimal(condition.getArguments().get(3)) // value
                );
            case "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW":
                return productRepo.compareDepositWithWithdraw(
                        userId,
                        condition.getArguments().get(0), // product type
                        condition.getArguments().get(1)  // operator
                );
            default:
                throw new IllegalArgumentException("Unknown query type: " + condition.getQueryType());
        }
    }
}
