--liquibase formatted sql

--changeset xhosh:1
create table users (
    id bigint generated always as identity primary key,
    username varchar(128) unique not null,
    password varchar(128) not null,
    firstname varchar(128),
    lastname varchar(128)
)