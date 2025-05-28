package pro.sky.StarBankApp.StarBankApp.staticRules;

import org.springframework.stereotype.Component;
import pro.sky.StarBankApp.StarBankApp.dto.Recommendation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class StaticRuleProcessor {
    private final List<RecommendationRuleSet> ruleSet;

    public StaticRuleProcessor(List<RecommendationRuleSet> ruleSet){
        this.ruleSet = ruleSet;
    }

    public List<Recommendation> processStaticRules(UUID userId){
        return ruleSet.stream()
                .map(rule -> rule.getRecommendation(userId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

}
