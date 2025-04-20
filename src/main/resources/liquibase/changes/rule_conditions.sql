--liquibase formatted sql
--changeset Yakovlev:create_table
--preconditions onfail:mark_ran onerror:halt
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

-- Создание индексов для улучшения производительности
CREATE INDEX idx_rule_conditions_rule_id ON rule_conditions(rule_id);