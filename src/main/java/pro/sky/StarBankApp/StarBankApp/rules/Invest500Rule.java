package pro.sky.StarBankApp.StarBankApp.rules;

import org.springframework.stereotype.Component;
import pro.sky.StarBankApp.StarBankApp.RecommendationRule;
import pro.sky.StarBankApp.StarBankApp.model.ProductRecommendation;
import pro.sky.StarBankApp.StarBankApp.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Component
public class Invest500Rule implements RecommendationRule {
    private final ProductRepository productRepository;
    private static final UUID PRODUCT_ID = UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a");

    public Invest500Rule(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional getRecommendation(UUID userId) {
        return Optional.empty();
    }

    @Override
    public Optional<Object> check(UUID userId) {

        boolean usesDebit = productRepository.isUserUsingProductType(userId, "DEBIT");
        boolean usesInvest = productRepository.isUserUsingProductType(userId, "INVEST");
        BigDecimal savingDeposits = productRepository.getTotalDepositsByProductType(userId, "SAVING");

        if (usesDebit && !usesInvest && savingDeposits.compareTo(new BigDecimal("1000")) > 0) {
            return Optional.ofNullable(productRepository.findProductById(PRODUCT_ID));
        }
        Optional<Object> invest500 = Optional.of(new ProductRecommendation(
                "Invest500",
                UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"),
                "Откройте свой путь к успеху с " +
                        "индивидуальным инвестиционным счетом (ИИС) от нашего банка! " +
                        "Воспользуйтесь налоговыми льготами и начните инвестировать с умом. " +
                        "Пополните счет до конца года и получите выгоду в виде вычета на взнос " +
                        "в следующем налоговом периоде. Не упустите возможность разнообразить " +
                        "свой портфель, снизить риски и следить за актуальными рыночными тенденциями. " +
                        "Откройте ИИС сегодня и станьте ближе к финансовой независимости!"
        ));

        return invest500;
    }


}
