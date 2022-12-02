--liquibase formatted sql
--changeset junior:202212011028
--comment: user table create

CREATE TABLE TB_CREDIT_CARDS
(
    id UUID NOT NULL primary key,
    holder_full_name VARCHAR(150) NOT NULL,
    holder_cpf VARCHAR(20) NOT NULL,
    credit_card_number VARCHAR(20) NOT NULL,
    expiration_date VARCHAR(10) NOT NULL,
    cvv_code VARCHAR(3) NOT NULL,
    user_id UUID NOT NULL,
    CONSTRAINT UK_USER_ID UNIQUE (user_id),
    CONSTRAINT FK_USER_CREDIT_CARD FOREIGN KEY (user_id) REFERENCES TB_USERS (id)
);

--rollback DROP TABLE TB_CREDIT_CARD;