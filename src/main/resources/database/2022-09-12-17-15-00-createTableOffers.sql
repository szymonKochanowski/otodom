--liquibase formatted sql
--changeset skochanowski:1

create table if not exists `thread`.`offers` (
id int AUTO_INCREMENT PRIMARY KEY,
created_on DATETIME,
number_of_links int
);