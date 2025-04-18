package pro.sky.StarBankApp.StarBankApp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.StarBankApp.StarBankApp.model.DynamicRule;
import pro.sky.StarBankApp.StarBankApp.service.DynamicRuleService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/rule")
public class RuleController {
    private final DynamicRuleService ruleService;

    public RuleController(DynamicRuleService ruleService) {
        this.ruleService = ruleService;
    }

    @PostMapping
    public ResponseEntity<DynamicRule> createRule(@RequestBody DynamicRule rule) {
        return ResponseEntity.ok(ruleService.createRule(rule));
    }

    @GetMapping
    public ResponseEntity<Map<String, List<DynamicRule>>> getAllRules() {
        return ResponseEntity.ok(Map.of("data", ruleService.getAllRules()));
    }

    @DeleteMapping("/{ruleId}")
    public ResponseEntity<Void> deleteRule(@PathVariable UUID ruleId) {
        ruleService.deleteRule(ruleId);
        return ResponseEntity.noContent().build();
    }
}
