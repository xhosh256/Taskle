DROP TABLE IF EXISTS task_tag CASCADE;
DROP TABLE IF EXISTS tasks CASCADE;
DROP TABLE IF EXISTS profiles CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS tags CASCADE;


create table if not exists users (
    id bigint generated always as identity primary key not null,
    username varchar(128) not null unique,
    password varchar(128) not null,
    created_at timestamp default now(),
    updated_at timestamp default now(),
    role varchar(32) not null
);

create table if not exists profiles (
    id bigint generated always as identity primary key not null,
    user_id bigint not null references users(id),
    firstname varchar(128),
    lastname varchar(128),
    birth_date date not null
);

create table if not exists tasks (
    id int generated always as identity primary key not null,
    user_id bigint not null references users(id),
    title varchar(128) not null,
    description text not null,
    created_at timestamp default now(),
    updated_at timestamp default now(),
    status varchar(32),
    difficulty varchar(32)
);

--create table if not exists subtasks (
--    id int generated always as identity primary key not null,
--    task_id bigint not null references tasks(id),
--    name varchar(128) not null,
--    created_at timestamp default now(),
--    status varchar(32)
--);

create table if not exists tags (
    id int generated always as identity primary key not null,
    name varchar(32) not null unique
);

create table if not exists task_tag (
    id int generated always as identity primary key not null,
    task_id int not null references tasks(id),
    tag_id int not null references tags(id)
)