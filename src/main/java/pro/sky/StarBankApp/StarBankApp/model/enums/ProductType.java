package pro.sky.StarBankApp.StarBankApp.model.enums;

public enum ProductType {
    DEBIT,
    CREDIT,
    INVEST,
    SAVING;

    public static ProductType fromString(String product) {
        for (ProductType type : ProductType.values()) {
            if (type.name().equalsIgnoreCase(product)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unsupported product type: " + product);
    }
}