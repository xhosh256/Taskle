--liquibase formatted sql

--changeset xhosh:1
alter table users drop column firstname;
alter table users drop column lastname;

--changeset xhosh:2
create table profiles (
    id bigint generated always as identity primary key,
    user_id bigint unique references users(id),
    firstname varchar(128),
    lastname varchar(128)
)