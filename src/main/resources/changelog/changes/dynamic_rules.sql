--liquibase formatted sql
--changeset Yakovlev:create_table
--preconditions onfail:mark_ran onerror:halt
-- Создание таблицы dynamic_rules
CREATE TABLE dynamic_rules (
    id UUID PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    product_id UUID NOT NULL,
    product_text TEXT NOT NULL
);