package pro.sky.StarBankApp.StarBankApp.model.enums;

public enum OperationType {
    DEPOSIT,
    WITHDRAW;

    public static OperationType fromString(String operation) {
        for (OperationType type : OperationType.values()) {
            if (type.name().equalsIgnoreCase(operation)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unsupported operation type: " + operation);
    }
}

