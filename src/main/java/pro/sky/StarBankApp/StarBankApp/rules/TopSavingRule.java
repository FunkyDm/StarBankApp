package pro.sky.StarBankApp.StarBankApp.rules;

import org.springframework.stereotype.Component;
import pro.sky.StarBankApp.StarBankApp.RecommendationRule;
import pro.sky.StarBankApp.StarBankApp.model.ProductRecommendation;
import pro.sky.StarBankApp.StarBankApp.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class TopSavingRule implements RecommendationRule {
    private final ProductRepository productRepository;
    private static final UUID PRODUCT_ID = UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925");

    public TopSavingRule(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductRecommendation check(UUID userId) {
        boolean usesDebit = productRepository.isUserUsingProductType(userId, "DEBIT");
        BigDecimal debitDeposits = productRepository.getTotalDepositsByProductType(userId, "DEBIT");
        BigDecimal savingDeposits = productRepository.getTotalDepositsByProductType(userId, "SAVING");
        BigDecimal debitSpends = productRepository.getTotalSpendsByProductType(userId, "DEBIT");

        boolean condition1 = debitDeposits.compareTo(new BigDecimal("50000")) >= 0 ||
                savingDeposits.compareTo(new BigDecimal("50000")) >= 0;
        boolean condition2 = debitDeposits.compareTo(debitSpends) > 0;

        if (usesDebit && condition1 && condition2) {
            return productRepository.findProductById(PRODUCT_ID);
        }
        return null;
    }
}
