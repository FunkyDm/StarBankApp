package pro.sky.StarBankApp.StarBankApp.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.sky.StarBankApp.StarBankApp.model.enums.ProductType;
import pro.sky.StarBankApp.StarBankApp.repository.RecommendationsRepository;

import java.util.List;
import java.util.UUID;

@Component
public class ActiveUserOfQuery extends AbstractQuery {

    @Autowired
    public ActiveUserOfQuery(RecommendationsRepository recommendationsRepository) {
        super(recommendationsRepository);
    }

    @Override
    protected boolean performInternal(UUID userId, RecommendationsRepository repo, List<String> arguments) {
        ProductType productType = ProductType.fromString(arguments.getFirst());
        return repo.getTransactionCount(userId, productType.name()) >= 5;
    }
}
