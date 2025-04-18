package pro.sky.StarBankApp.StarBankApp.rules;

import org.springframework.stereotype.Component;
import pro.sky.StarBankApp.StarBankApp.RecommendationRule;
import pro.sky.StarBankApp.StarBankApp.model.ProductRecommendation;
import pro.sky.StarBankApp.StarBankApp.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Component
public class SimpleCreditRule implements RecommendationRule {
    private final ProductRepository productRepository;
    private static final UUID PRODUCT_ID = UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f");

    public SimpleCreditRule(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional getRecommendation(UUID userId) {
        return Optional.empty();
    }

    @Override
    public Optional<Object> check(UUID userId) {
        boolean usesCredit = productRepository.isUserUsingProductType(userId, "CREDIT");
        BigDecimal debitDeposits = productRepository.getTotalDepositsByProductType(userId, "DEBIT");
        BigDecimal debitSpends = productRepository.getTotalSpendsByProductType(userId, "DEBIT");

        boolean condition1 = debitDeposits.compareTo(debitSpends) > 0;
        boolean condition2 = debitSpends.compareTo(new BigDecimal("100000")) > 0;

        if (!usesCredit && condition1 && condition2) {
            return Optional.ofNullable(productRepository.findProductById(PRODUCT_ID));
        }
        return Optional.of(new ProductRecommendation(
                "Простой кредит",
                UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"),
                "Откройте мир выгодных кредитов с нами!" +
                        "Ищете способ быстро и без лишних хлопот получить нужную сумму? " +
                        "Тогда наш выгодный кредит — именно то, что вам нужно! " +
                        "Мы предлагаем низкие процентные ставки, гибкие условия и " +
                        "индивидуальный подход к каждому клиенту." +
                        "Почему выбирают нас:" +
                        "Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс " +
                        "рассмотрения заявки занимает всего несколько часов." +
                        "Удобное оформление. Подать заявку на кредит можно онлайн " +
                        "на нашем сайте или в мобильном приложении." +
                        "Широкий выбор кредитных продуктов. Мы предлагаем кредиты на " +
                        "различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое." +
                        "Не упустите возможность воспользоваться выгодными " +
                        "условиями кредитования от нашей компании!"
        ));
    }
    }
