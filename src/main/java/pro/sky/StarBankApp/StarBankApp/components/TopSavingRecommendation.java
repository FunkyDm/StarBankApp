package pro.sky.StarBankApp.StarBankApp.components;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pro.sky.StarBankApp.StarBankApp.model.Recommendation;
import pro.sky.StarBankApp.StarBankApp.repository.RecommendationsRepository;
import ruleset.RecommendationRuleSet;

import java.util.Optional;
import java.util.UUID;


@Component
@AllArgsConstructor
public class TopSavingRecommendation implements RecommendationRuleSet {
    private final RecommendationsRepository recommendationsRepository;

    @Override
    public Optional<Recommendation> getRecommendation(UUID userId) {
        int countDebit = recommendationsRepository.getTransactionCount(userId, "DEBIT");
        long sumDepositDebit = recommendationsRepository.
                getTransactionAmount(userId, "DEBIT", "DEPOSIT");
        long sumDepositSaving = recommendationsRepository.
                getTransactionAmount(userId, "SAVING", "DEPOSIT");
        long sumWithdrawDebit = recommendationsRepository.
                getTransactionAmount(userId, "DEBIT", "WITHDRAW");
        if (countDebit > 0 & (sumDepositSaving > 50000 || sumDepositDebit > 50000)
                & sumWithdrawDebit < sumDepositDebit) {
            return Optional.of(new Recommendation(
                    "Top Saving",
                    UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"),
                    "Откройте свою собственную «Копилку» с нашим банком! " +
                            "«Копилка» — это уникальный банковский инструмент, " +
                            "который поможет вам легко и удобно накапливать деньги на важные цели. " +
                            "Больше никаких забытых чеков и потерянных квитанций — всё под контролем!" +
                            "\n" +
                            "Преимущества «Копилки»:\n" +
                            "\n" +
                            "Накопление средств на конкретные цели. " +
                            "Установите лимит и срок накопления, и банк будет автоматически переводить определенную сумму на ваш счет.\n" +
                            "\n" +
                            "Прозрачность и контроль. Отслеживайте свои доходы и расходы, " +
                            "контролируйте процесс накопления и корректируйте стратегию при необходимости.\n" +
                            "\n" +
                            "Безопасность и надежность. Ваши средства находятся под защитой банка, " +
                            "а доступ к ним возможен только через мобильное приложение или интернет-банкинг.\n" +
                            "\n" +
                            "Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!"
            ));
        }
        return Optional.empty();
    }


}
