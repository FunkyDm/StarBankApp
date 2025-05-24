package pro.sky.StarBankApp.StarBankApp.staticRules;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pro.sky.StarBankApp.StarBankApp.dto.Recommendation;
import pro.sky.StarBankApp.StarBankApp.repository.RecommendationsRepository;

import java.util.Optional;
import java.util.UUID;

//Создаём компонент интерфейса для рекомендации Простой кредит
@Component
@AllArgsConstructor
public class SimpleCreditRecommendation implements RecommendationRuleSet {
    private final RecommendationsRepository recommendationsRepository;

    @Override
    public Optional<Recommendation> getRecommendation(UUID userId) {
        int countCredit = recommendationsRepository.getTransactionCount(userId, "CREDIT");
        long sumDepositDebit = recommendationsRepository.
                getTransactionAmount(userId, "DEBIT", "DEPOSIT");
        long sumWithdrawDebit = recommendationsRepository.
                getTransactionAmount(userId, "DEBIT", "WITHDRAW");
        if (countCredit == 0 & sumWithdrawDebit < sumDepositDebit & sumWithdrawDebit > 100_000) {
            return Optional.of(new Recommendation(
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
        return Optional.empty();
    }
}