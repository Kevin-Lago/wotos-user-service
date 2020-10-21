CREATE SCHEMA IF NOT EXISTS wotos_user;
USE wotos_user;

create table if not exists users (

    user_id int not null auto_increment primary key,
    username varchar(50),
    password varchar(255),
    active bit,
    roles varchar(255),
    dark_mode bit,
    stuff varchar(255),
    UNIQUE(username)

);

