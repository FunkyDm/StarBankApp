--liquibase formatted sql
--changeset Yakovlev:create_table
--preconditions onfail:mark_ran onerror:halt
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
CREATE INDEX idx_rule_arguments_condition_id ON rule_arguments(condition_id);