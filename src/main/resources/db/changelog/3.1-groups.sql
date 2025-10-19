--liquibase formatted sql

--changeset xhosh:1
create table groups (
    id int generated always as identity primary key,
    user_id bigint references users(id),
    name varchar(128) not null,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now()
)

--changeset xhosh:2
create table group_task (
    id int generated always as identity primary key,
    task_id int references tasks(id),
    group_id int references groups(id)
)