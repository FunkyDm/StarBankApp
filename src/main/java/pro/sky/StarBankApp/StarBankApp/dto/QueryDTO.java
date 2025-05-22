package pro.sky.StarBankApp.StarBankApp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pro.sky.StarBankApp.StarBankApp.model.queries.Query;

import java.util.List;

@Getter
@AllArgsConstructor
public class QueryDTO {
    @JsonProperty("query")
    private String query;

    @JsonProperty("arguments")
    private List<String> arguments;

    @JsonProperty("negate")
    private boolean negate;

    public static QueryDTO fromEntity(Query query) {
        return new QueryDTO(
                query.getQueryType().name(), // Предполагаю, что queryType — это Enum
                query.getArguments(),
                query.isNegate()
        );
    }

    public Query toEntity() {
        Query query = new Query();
        query.setQueryType(QueryType.valueOf(this.query));
        query.setArguments(this.arguments);
        query.setNegate(this.negate);
        return query;
    }

}
