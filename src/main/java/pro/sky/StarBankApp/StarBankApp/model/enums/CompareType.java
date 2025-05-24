package pro.sky.StarBankApp.StarBankApp.model.enums;

import lombok.Getter;

@Getter
public enum CompareType {
    LT("<"),
    GT(">"),
    EQ("="),
    LE("<="),
    GE(">=");

    private final String symbol;

    CompareType(String symbol) {
        this.symbol = symbol;
    }

    // Метод для преобразования строки в CompareType
    public static CompareType fromString(String symbol) {
        for (CompareType type : CompareType.values()) {
            if (type.symbol.equals(symbol)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unsupported operation symbol: " + symbol);
    }
}
