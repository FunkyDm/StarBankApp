package pro.sky.StarBankApp.StarBankApp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.StarBankApp.StarBankApp.dto.DynamicRuleDTO;
import pro.sky.StarBankApp.StarBankApp.service.RuleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rule")
public class DynamicRuleController {
//    private final DynamicRuleService ruleService;
//
//    public DynamicRuleController(DynamicRuleService ruleService) {
//        this.ruleService = ruleService;
//    }
//
//    @PostMapping
//    public ResponseEntity<DynamicRule> createRule(@RequestBody DynamicRule rule) {
//        return ResponseEntity.ok(ruleService.createRule(rule));
//    }
//
//    @GetMapping
//    public ResponseEntity<Map<String, List<DynamicRule>>> getAllRules() {
//        return ResponseEntity.ok(Map.of("data", ruleService.getAllRules()));
//    }

    private final RuleStatService ruleStatService;
    private final RuleService ruleService;

    @PostMapping
    public ResponseEntity<DynamicRuleDTO> createRule(@RequestBody DynamicRuleDTO ruleDTO) {
        DynamicRuleDTO createdRule = ruleService.createRule(ruleDTO);
        return ResponseEntity.ok(createdRule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable UUID id) {
        ruleService.deleteRule(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Map<String, List<DynamicRuleDTO>>> getAllRules() {
        List<DynamicRuleDTO> rules = ruleService.getAllRules();
        return ResponseEntity.ok(Map.of("data", rules));
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, List<RuleStatDTO>>> getStats() {
        List<RuleStatDTO> stats = ruleStatService.getAllStats();
        Map<String, List<RuleStatDTO>> response = new HashMap<>();
        response.put("stats", stats);
        return ResponseEntity.ok(response);
    }

}
