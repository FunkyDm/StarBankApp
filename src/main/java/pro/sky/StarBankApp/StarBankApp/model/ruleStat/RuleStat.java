package pro.sky.StarBankApp.StarBankApp.model.ruleStat;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RuleStat {

    @Id
    private UUID ruleId;

    private int count;

    public RuleStat(UUID ruleId) {
        this.ruleId = ruleId;
        this.count = 0;
    }

    public void incrementCount() {
        this.count++;
    }

    public void resetCount() {
        this.count = 0;
    }
}