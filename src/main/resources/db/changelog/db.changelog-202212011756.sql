--liquibase formatted sql
--changeset junior:202212011028
--comment: user table create

ALTER TABLE TB_USERS
    ADD COLUMN payment_status varchar(20) not null,
    ADD COLUMN payment_expiration_date timestamp with time zone,
    ADD COLUMN first_payment_date timestamp with time zone,
    ADD COLUMN last_payment_date timestamp with time zone;

--rollback ALTER TABLE TB_USERS DROP COLUMN payment_status, DROP COLUMN payment_expiration_date, DROP COLUMN first_payment_date, DROP COLUMN last_payment_date;