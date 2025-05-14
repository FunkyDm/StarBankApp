package pro.sky.StarBankApp.StarBankApp.rules;

import org.springframework.stereotype.Component;
import pro.sky.StarBankApp.StarBankApp.dto.RecommendationResponse;
import pro.sky.StarBankApp.StarBankApp.repository.TransactionRepository;

import java.util.Optional;

@Component
public class Invest500Rule implements RecommendationRuleSet {
    private final TransactionRepository transactionRepository;

    public Invest500Rule(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Optional<RecommendationResponse.Recommendation> apply(String userId) {
        boolean usesDebit = transactionRepository.usesProductType(userId, "DEBIT");
        boolean usesInvest = transactionRepository.usesProductType(userId, "INVEST");
        double savingDeposits = transactionRepository.getTotalDepositByType(userId, "SAVING");

        if (usesDebit && !usesInvest && savingDeposits > 1000) {
            return Optional.of(new RecommendationResponse.Recommendation(
                    "Invest 500",
                    "147f6a0f-3b91-413b-ab99-87f081d60d5a",
                    "Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка!"
            ));
        }

        return Optional.empty();
    }

}