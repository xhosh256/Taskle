--liquibase formatted sql

--changeset xhosh:1
alter table users
add column role varchar(16) not null