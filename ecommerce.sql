create database if not exists newecommerce;
 
use newecommerce;
 
drop table if exists products;

create table products (
   id int,
   brand varchar(50),
   description varchar(150),
   category varchar(50),
   type varchar(50),
   price float,
   image_src varchar(50),
   primary key (id));
 
insert into products values (1001, 'HOLBEIN', 'Artist Acrylic Ink 100ml Colorless', 'ACRYLIC', 'ink', 16.90, 'image/acrylic/ink/holbein_1.jpg');
insert into products values (1002, 'HOLBEIN', 'Artist Acrylic Ink 100ml Colorless', 'ACRYLIC', 'ink', 12.90, 'image/acrylic/ink/holbein_2.jpg');
insert into products values (1003, 'SCHMINCKE', 'Aero Color Acrylic Ink 28ml Aero Metallic Brilliant Gold', 'ACRYLIC', 'ink', 14.89, 'image/acrylic/ink/schmincke_3.jpg');
insert into products values (1004, 'SCHMINCKE', 'Aero Color Acrylic Ink 28ml Aero Metallic Brilliant Bronze', 'ACRYLIC', 'ink', 14.89, 'image/acrylic/ink/schmincke_4.jpg');

insert into products values (1005, 'HOLBEIN', 'Duo Water-Mixable Oil 10ml Compact Set x 10', 'OIL', 'paint', 26.90, 'image/oil/paint/holbein_1.jpg');
insert into products values (1006, 'HOLBEIN', 'Duo Water-Mixable Oil 10ml Starter Set x 12', 'OIL', 'paint', 32.90, 'image/oil/paint/holbein_2.jpg');
insert into products values (1007, 'HOLBEIN', 'Duo Water-Mixable Oil 15ml Basic Set x 12', 'OIL', 'paint', 39.90, 'image/oil/paint/holbein_3.jpg');
insert into products values (1008, 'HOLBEIN', 'Duo Water-Mixable Oil 20ml Set x 12', 'OIL', 'paint', 69.90, 'image/oil/paint/holbein_4.jpg');

insert into products values (1009, 'SCHMINCKE', 'Aqua Drop Watercolor Ink 30ml Liner Set x 5', 'WATERCOLOR', 'ink', 49.90, 'image/watercolor/ink/schmincke_1.jpg');
insert into products values (1010, 'SCHMINCKE', 'Aqua Drop Empty Brush Liner', 'WATERCOLOR', 'ink', 6.90, 'image/watercolor/ink/schmincke_2.jpg');
insert into products values (1011, 'SCHMINCKE', 'Aqua Drop Watercolor Ink 30ml Opaque White', 'WATERCOLOR', 'ink', 8.90, 'image/watercolor/ink/schmincke_3.jpg');
insert into products values (1012, 'SCHMINCKE', 'Aqua Drop Watercolor Ink 30ml Deep Black', 'WATERCOLOR', 'ink', 9.90, 'image/watercolor/ink/schmincke_4.jpg');



select * from products;