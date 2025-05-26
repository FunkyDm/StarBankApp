package pro.sky.StarBankApp.StarBankApp.controller;



import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.StarBankApp.StarBankApp.service.RuleStatService;


@RestController
@AllArgsConstructor
@RequestMapping("/management")
public class RuleStatController {

    private final RuleStatService ruleStatService;

    @PostMapping("/clear-caches")
    public ResponseEntity<Void> clearCaches() {
        ruleStatService.resetAllStats();
        return ResponseEntity.noContent().build();
    }
}

