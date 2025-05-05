--luquibase formatted sql

--changeset Dm:1

CREATE TABLE dinamic_rules (
    id UUID PRIMARY KEY NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    product_id UUID NOT NULL,
    product_text TEXT NOT NULL,
    rule JSONB NOT NULL
);