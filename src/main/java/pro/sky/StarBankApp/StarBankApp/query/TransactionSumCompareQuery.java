package pro.sky.StarBankApp.StarBankApp.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.sky.StarBankApp.StarBankApp.model.enums.CompareType;
import pro.sky.StarBankApp.StarBankApp.model.enums.OperationType;
import pro.sky.StarBankApp.StarBankApp.model.enums.ProductType;
import pro.sky.StarBankApp.StarBankApp.repository.RecommendationsRepository;

import java.util.List;
import java.util.UUID;

@Component
public class TransactionSumCompareQuery extends AbstractQuery {

    @Autowired
    public TransactionSumCompareQuery(RecommendationsRepository recommendationsRepository) {
        super(recommendationsRepository);
    }

    @Override
    protected boolean performInternal(UUID userId, RecommendationsRepository repo, List<String> arguments) {
        // Преобразуем строки в enum
        ProductType productType = ProductType.fromString(arguments.get(0)); // Например, "DEBIT"
        OperationType operationType = OperationType.fromString(arguments.get(1)); // Например, "DEPOSIT"
        CompareType compareType = CompareType.fromString(arguments.get(2)); // Например, ">"
        long threshold = Long.parseLong(arguments.get(3)); // Число для сравнения

        // Получаем сумму транзакций по заданному типу продукта и транзакции
        long transactionSum = repo.getTransactionAmount(userId, productType.name(), operationType.name());

        // Выполняем операцию сравнения
        return switch (compareType) {
            case LT -> transactionSum < threshold;
            case GT -> transactionSum > threshold;
            case EQ -> transactionSum == threshold;
            case LE -> transactionSum <= threshold;
            case GE -> transactionSum >= threshold;
            default -> throw new IllegalArgumentException("Unsupported compare type: " + compareType);
        };
    }
}


