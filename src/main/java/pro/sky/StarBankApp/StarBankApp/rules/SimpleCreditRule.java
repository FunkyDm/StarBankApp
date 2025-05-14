package pro.sky.StarBankApp.StarBankApp.rules;

import org.springframework.stereotype.Component;
import pro.sky.StarBankApp.StarBankApp.dto.RecommendationResponse;
import pro.sky.StarBankApp.StarBankApp.repository.TransactionRepository;

import java.util.Optional;

@Component
public class SimpleCreditRule implements RecommendationRuleSet {
    private final TransactionRepository transactionRepository;

    public SimpleCreditRule(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Optional<RecommendationResponse.Recommendation> apply(String userId) {
        boolean usesCredit = transactionRepository.usesProductType(userId, "CREDIT");
        double debitDeposit = transactionRepository.getTotalDepositByType(userId, "DEBIT");
        double debitSpends = transactionRepository.getTotalSpendByType(userId, "DEBIT");

        if (!usesCredit && debitDeposit > debitSpends && debitSpends > 100000) {
            return Optional.of(new RecommendationResponse.Recommendation(
                    "Simple Credit",
                    "ab138afb-f3ba-4a93-b74f-0fcee86d447f",
                    "Откройте мир выгодных кредитов с нами!"
            ));
        }

        return Optional.empty();
    }
}