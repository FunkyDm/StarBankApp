-- Создание таблицы dynamic_rules
CREATE TABLE dynamic_rules (
    id UUID PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    product_id UUID NOT NULL,
    product_text TEXT NOT NULL
);

-- Создание таблицы rule_conditions
CREATE TABLE rule_conditions (
    id UUID PRIMARY KEY,
    rule_id UUID NOT NULL,
    query_type VARCHAR(50) NOT NULL,
    negate BOOLEAN NOT NULL,
    CONSTRAINT fk_rule
        FOREIGN KEY (rule_id)
        REFERENCES dynamic_rules(id)
        ON DELETE CASCADE
);

-- Создание таблицы rule_arguments
CREATE TABLE rule_arguments (
    condition_id UUID NOT NULL,
    argument VARCHAR(100) NOT NULL,
    CONSTRAINT fk_condition
        FOREIGN KEY (condition_id)
        REFERENCES rule_conditions(id)
        ON DELETE CASCADE,
    PRIMARY KEY (condition_id, argument)
);

-- Создание индексов для улучшения производительности
CREATE INDEX idx_rule_conditions_rule_id ON rule_conditions(rule_id);
CREATE INDEX idx_rule_arguments_condition_id ON rule_arguments(condition_id);