--liquibase formatted sql

--changeset xhosh:1
alter table tags
rename column tag_name to name;