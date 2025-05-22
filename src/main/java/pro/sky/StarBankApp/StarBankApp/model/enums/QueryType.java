package pro.sky.StarBankApp.StarBankApp.model.enums;

public enum QueryType {
    USER_OF,
    ACTIVE_USER_OF,
    TRANSACTION_SUM_COMPARE,
    TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW;

    public static QueryType fromString(String query) {
        for (QueryType type : QueryType.values()) {
            if (type.name().equalsIgnoreCase(query)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unsupported query type: " + query);
    }
}
