--liquibase formatted sql

--changeset xhosh:1
create table tasks (
    id int generated always as identity not null primary key,
    user_id bigint not null references users(id),
    title varchar(128) not null,
    description text not null
)