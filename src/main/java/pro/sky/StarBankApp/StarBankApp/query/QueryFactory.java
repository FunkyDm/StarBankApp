package pro.sky.StarBankApp.StarBankApp.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.sky.StarBankApp.StarBankApp.model.enums.QueryType;
import pro.sky.StarBankApp.StarBankApp.repository.RecommendationsRepository;

import java.util.List;


@Component
public class QueryFactory {

    private static RecommendationsRepository recommendationRepo;

    @Autowired
    public QueryFactory(RecommendationsRepository recommendationRepo) {
        this.recommendationRepo = recommendationRepo;
    }

    public static AbstractQuery from(QueryType queryType, List<String> arguments, boolean negate) {
        switch (queryType) {
            case USER_OF:
                return new UserOfQuery(recommendationRepo);
            case ACTIVE_USER_OF:
                return new ActiveUserOfQuery(recommendationRepo);
            case TRANSACTION_SUM_COMPARE:
                return new TransactionSumCompareQuery(recommendationRepo);
            case TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW:
                return new TransactionSumCompareDepositWithdrawQuery(recommendationRepo);
            default:
                throw new IllegalArgumentException("Unsupported query type: " + queryType);
        }
    }
}


