--liquibase formatted sql
--changeset junior:202212011028
--comment: user table create

CREATE TABLE TB_USERS(
    id uuid not null primary key,
    email varchar(50) unique not null,
    full_name varchar(150) not null,
    user_status varchar(20) not null,
    user_type varchar(20) not null,
    cpf varchar(20),
    phone_number varchar(20)
);

--rollback DROP TABLE TB_USERS;