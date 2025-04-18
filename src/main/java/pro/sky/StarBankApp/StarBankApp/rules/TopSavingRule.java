package pro.sky.StarBankApp.StarBankApp.rules;

import org.springframework.stereotype.Component;
import pro.sky.StarBankApp.StarBankApp.RecommendationRule;
import pro.sky.StarBankApp.StarBankApp.model.ProductRecommendation;
import pro.sky.StarBankApp.StarBankApp.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Component
public class TopSavingRule implements RecommendationRule {
    private final ProductRepository productRepository;
    private static final UUID PRODUCT_ID = UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925");

    public TopSavingRule(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional getRecommendation(UUID userId) {
        return Optional.empty();
    }

    @Override
    public Optional<Object> check(UUID userId) {
        boolean usesDebit = productRepository.isUserUsingProductType(userId, "DEBIT");
        BigDecimal debitDeposits = productRepository.getTotalDepositsByProductType(userId, "DEBIT");
        BigDecimal savingDeposits = productRepository.getTotalDepositsByProductType(userId, "SAVING");
        BigDecimal debitSpends = productRepository.getTotalSpendsByProductType(userId, "DEBIT");

        boolean condition1 = debitDeposits.compareTo(new BigDecimal("50000")) >= 0 ||
                savingDeposits.compareTo(new BigDecimal("50000")) >= 0;
        boolean condition2 = debitDeposits.compareTo(debitSpends) > 0;

        if (usesDebit && condition1 && condition2) {
            return Optional.ofNullable(productRepository.findProductById(PRODUCT_ID));
        }
        return Optional.of(new ProductRecommendation(
                "Top Saving",
                UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"),
                "Откройте свою собственную «Копилку» с нашим банком! " +
                        "«Копилка» — это уникальный банковский инструмент, " +
                        "который поможет вам легко и удобно накапливать деньги на важные цели. " +
                        "Больше никаких забытых чеков и потерянных квитанций — всё под контролем!" +
                        "Преимущества «Копилки»:" +
                        "Накопление средств на конкретные цели. " +
                        "Установите лимит и срок накопления, и банк будет автоматически переводить определенную сумму на ваш счет." +
                        "Прозрачность и контроль. Отслеживайте свои доходы и расходы, " +
                        "контролируйте процесс накопления и корректируйте стратегию при необходимости." +
                        "Безопасность и надежность. Ваши средства находятся под защитой банка, " +
                        "а доступ к ним возможен только через мобильное приложение или интернет-банкинг." +
                        "Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!"
        ));
    }
}
