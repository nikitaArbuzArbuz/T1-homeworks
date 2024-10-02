-- liquibase formatted sql

CREATE SEQUENCE IF NOT EXISTS account_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE account
(
    id BIGINT NOT NULL,
    account_type varchar(50),
    balance DECIMAL(19, 2),
    CONSTRAINT pk_account PRIMARY KEY (id)
);

