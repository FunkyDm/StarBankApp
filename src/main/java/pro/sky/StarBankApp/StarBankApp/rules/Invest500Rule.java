package pro.sky.StarBankApp.StarBankApp.rules;

import org.springframework.stereotype.Component;
import pro.sky.StarBankApp.StarBankApp.RecommendationRule;
import pro.sky.StarBankApp.StarBankApp.model.ProductRecommendation;
import pro.sky.StarBankApp.StarBankApp.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class Invest500Rule implements RecommendationRule {
    private final ProductRepository productRepository;
    private static final UUID PRODUCT_ID = UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a");

    public Invest500Rule(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductRecommendation check(UUID userId) {

        boolean usesDebit = productRepository.isUserUsingProductType(userId, "DEBIT");
        boolean usesInvest = productRepository.isUserUsingProductType(userId, "INVEST");
        BigDecimal savingDeposits = productRepository.getTotalDepositsByProductType(userId, "SAVING");

        if (usesDebit && !usesInvest && savingDeposits.compareTo(new BigDecimal("1000")) > 0) {
            return productRepository.findProductById(PRODUCT_ID);
        }
        return null;
    }
}
