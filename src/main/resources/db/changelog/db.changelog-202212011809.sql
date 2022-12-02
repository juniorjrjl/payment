--liquibase formatted sql
--changeset junior:202212011028
--comment: user table create

CREATE TABLE TB_PAYMENTS
(
    id UUID not null primary key,
    payment_control VARCHAR(255) not null,
    request_date TIMESTAMP with time zone not null,
    completion_date TIMESTAMP with time zone,
    expiration_date TIMESTAMP with time zone not null,
    last_digit_credit_card VARCHAR(4) not null,
    value_paid DECIMAL not null,
    message VARCHAR(150),
    recurrence BOOLEAN not null,
    user_id UUID not null,
    CONSTRAINT FK_USERS_PAYMENTS FOREIGN KEY(user_id) REFERENCES TB_USERS(id)
);

--rollback DROP TABLE TB_PAYMENTS;