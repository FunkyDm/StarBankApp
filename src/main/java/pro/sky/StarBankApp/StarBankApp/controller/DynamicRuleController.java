package pro.sky.StarBankApp.StarBankApp.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.StarBankApp.StarBankApp.service.DynamicRuleService;

import java.util.List;
import java.util.Map;

@Profile("!disabledModule")
@RestController
@RequestMapping("/rule")
public class DynamicRuleController {
    private final DynamicRuleService ruleService;

    public DynamicRuleController(DynamicRuleService ruleService) {
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

//    @DeleteMapping("/{ruleId}")
//    public ResponseEntity<Void> deleteRule(@PathVariable UUID ruleId) {
//        ruleService.deleteRule(ruleId);
//        return ResponseEntity.noContent().build();
//    }

}
