create database if not exists newecommerce;
 
use newecommerce;
 
drop table if exists users;

create table users (
   id int AUTO_INCREMENT,
   email varchar(50),
   name varchar(50),
   password varchar(50),
   primary key (id) 
);

insert into users values (1, 'admin@gmail.com', 'admin', 'admin');

select * from users;