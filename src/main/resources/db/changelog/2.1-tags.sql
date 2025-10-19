--liquibase formatted sql

--changeset xhosh:1
create table tags (
    id int generated always as identity primary key,
    tag_name varchar(32) not null
);

--changeset xhosh:2
create table task_tag (
    id bigint generated always as identity primary key,
    task_id int references tasks(id),
    tag_id int references tags(id)
);


--changeset xhosh:3
insert into tags (tag_name)
values ('WORK');

insert into tags (tag_name)
values ('HOME');