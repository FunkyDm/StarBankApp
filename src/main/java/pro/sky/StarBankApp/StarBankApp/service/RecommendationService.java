package pro.sky.StarBankApp.StarBankApp.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.StarBankApp.StarBankApp.dto.DynamicRuleDTO;
import pro.sky.StarBankApp.StarBankApp.dto.Recommendation;
import pro.sky.StarBankApp.StarBankApp.model.enums.QueryType;
import pro.sky.StarBankApp.StarBankApp.query.AbstractQuery;
import pro.sky.StarBankApp.StarBankApp.query.QueryFactory;
import pro.sky.StarBankApp.StarBankApp.repository.RuleRepository;
import pro.sky.StarBankApp.StarBankApp.staticRules.StaticRuleProcessor;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendationService {
    private final StaticRuleProcessor staticRuleProcessor;
    private final RuleRepository ruleRepository;
    private final RuleStatService ruleStatService;

    @Transactional
    public List<Recommendation> getRecommendations(UUID userId) {
        List<Recommendation> dynamicRecommendations = getDynamicRecommendations(userId);
        List<Recommendation> staticRecommendations = staticRuleProcessor.processStaticRules(userId);

        //Объединяем рекомендации, избегая дубликатов
        return Stream.concat(
                dynamicRecommendations.stream(),
                staticRecommendations.stream()
        )
                .distinct()
                .collect(Collectors.toList());
    }

    private List<Recommendation> getDynamicRecommendations(UUID userId){
        List<DynamicRuleDTO> rules = ruleRepository.findAll()
                .stream()
                .map(DynamicRuleDTO::fromEntity)
                .toList();

        return rules.stream()
                .filter(ruleDTO -> {
                    boolean isMatch = process(ruleDTO, userId);
                    if (isMatch) {
                        ruleStatService.incrementStat(ruleDTO.getProductId()); // Обновляем статистику
                    }
                    return isMatch;
                })
                .map(this::toRecommendation)
                .collect(Collectors.toList());
    }

    public boolean process(DynamicRuleDTO ruleDTO, UUID userId) {
        return ruleDTO.getRule().stream()
                .map(queryDTO -> {
                    // Здесь вам нужно создать AbstractQuery и выполнить проверку на основании QueryDTO
                    AbstractQuery abstractQuery = QueryFactory.from(
                            QueryType.valueOf(queryDTO.getQuery()),
                            queryDTO.getArguments(),
                            queryDTO.isNegate());
                    return abstractQuery.perform(userId, queryDTO.getArguments());
                })
                .reduce((a, b) -> a && b)
                .orElse(false);
    }

    public Recommendation toRecommendation(DynamicRuleDTO ruleDTO) {
        return new Recommendation(
                ruleDTO.getProductName(),
                ruleDTO.getProductId(),
                ruleDTO.getProductText());
    }

}

