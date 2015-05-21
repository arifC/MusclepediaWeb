# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table benutzer (
  benutzer_id               varchar(40) primary key,
  name                      varchar(255),
  email                     varchar(255),
  passwort                  varchar(255))
;

create table trainingsplan (
  plan_id                   integer primary key AUTOINCREMENT,
  typ                       varchar(255))
;

create table uebungen (
  uebung_id                 integer primary key AUTOINCREMENT,
  name                      varchar(255),
  mu                        integer,
  beschreibung              varchar(255),
  schwierigkeit             varchar(255),
  constraint ck_uebungen_mu check (mu in (0,1,2,3)))
;




# --- !Downs

PRAGMA foreign_keys = OFF;

drop table benutzer;

drop table trainingsplan;

drop table uebungen;

PRAGMA foreign_keys = ON;

