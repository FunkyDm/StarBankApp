package pro.sky.StarBankApp.StarBankApp.rules;

import org.springframework.stereotype.Component;
import pro.sky.StarBankApp.StarBankApp.RecommendationRule;
import pro.sky.StarBankApp.StarBankApp.model.ProductRecommendation;
import pro.sky.StarBankApp.StarBankApp.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class SimpleCreditRule implements RecommendationRule {
    private final ProductRepository productRepository;
    private static final UUID PRODUCT_ID = UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f");

    public SimpleCreditRule(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductRecommendation check(UUID userId) {
        boolean usesCredit = productRepository.isUserUsingProductType(userId, "CREDIT");
        BigDecimal debitDeposits = productRepository.getTotalDepositsByProductType(userId, "DEBIT");
        BigDecimal debitSpends = productRepository.getTotalSpendsByProductType(userId, "DEBIT");

        boolean condition1 = debitDeposits.compareTo(debitSpends) > 0;
        boolean condition2 = debitSpends.compareTo(new BigDecimal("100000")) > 0;

        if (!usesCredit && condition1 && condition2) {
            return productRepository.findProductById(PRODUCT_ID);
        }
        return null;
    }
}