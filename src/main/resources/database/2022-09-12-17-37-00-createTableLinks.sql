--liquibase formatted sql
--changeset skochanowski:2

create table if not exists `thread`.`links` (
id int AUTO_INCREMENT PRIMARY KEY,
URL VARCHAR(255),
offer_id int
);