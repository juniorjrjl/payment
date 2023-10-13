--liquibase formatted sql
--changeset junior:202212011819
--comment: rename payment_control to control

ALTER TABLE TB_PAYMENTS
    RENAME COLUMN payment_control TO control;

--rollback ALTER TABLE TB_PAYMENTS RENAME COLUMN control TO payment_control;