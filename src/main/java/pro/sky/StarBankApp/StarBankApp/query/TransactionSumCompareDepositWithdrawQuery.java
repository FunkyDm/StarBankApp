package pro.sky.StarBankApp.StarBankApp.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.sky.StarBankApp.StarBankApp.model.enums.CompareType;
import pro.sky.StarBankApp.StarBankApp.model.enums.ProductType;
import pro.sky.StarBankApp.StarBankApp.repository.RecommendationsRepository;

import java.util.List;
import java.util.UUID;

@Component
public class TransactionSumCompareDepositWithdrawQuery extends AbstractQuery {

    @Autowired
    public TransactionSumCompareDepositWithdrawQuery(RecommendationsRepository recommendationsRepository) {
        super(recommendationsRepository);
    }

    @Override
    protected boolean performInternal(UUID userId, RecommendationsRepository repo, List<String> arguments) {
        // Получаем аргументы
        ProductType productType = ProductType.fromString(arguments.get(0)); // Тип продукта, например "DEBIT"
        CompareType compareType = CompareType.fromString(arguments.get(1));

        // Получаем сумму транзакций с типом DEPOSIT и WITHDRAW для указанного продукта
        long depositSum = repo.getTransactionAmount(userId, productType.name(), "DEPOSIT");
        long withdrawSum = repo.getTransactionAmount(userId, productType.name(), "WITHDRAW");

        // Выполняем операцию сравнения
        return switch (compareType) {
            case LT -> depositSum < withdrawSum;
            case GT -> depositSum > withdrawSum;
            case EQ -> depositSum == withdrawSum;
            case LE -> depositSum <= withdrawSum;
            case GE -> depositSum >= withdrawSum;
            default -> throw new IllegalArgumentException("Unsupported compare type: " + compareType);
        };
    }
}
