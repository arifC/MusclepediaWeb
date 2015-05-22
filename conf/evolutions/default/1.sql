# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table benutzer (
  benutzer_id               varchar(40) not null,
  name                      varchar(255),
  email                     varchar(255),
  passwort                  varchar(255),
  studio_id                 integer,
  test                      varchar(255),
  constraint pk_benutzer primary key (benutzer_id))
;

create table studio (
  id                        integer auto_increment not null,
  plz                       varchar(255),
  ort                       varchar(255),
  strasse                   varchar(255),
  name                      varchar(255),
  gesamt_bewertung          double,
  gesamt_ausstattung        double,
  gesamt_lage               double,
  gesamt_preis              double,
  gesamt_service            double,
  constraint pk_studio primary key (id))
;

create table trainingsplan (
  plan_id                   integer auto_increment not null,
  typ                       varchar(255),
  constraint pk_trainingsplan primary key (plan_id))
;

create table uebung (
  uebung_id                 integer auto_increment not null,
  name                      varchar(255),
  beschreibung              varchar(255),
  schwierigkeit             varchar(255),
  constraint pk_uebung primary key (uebung_id))
;

alter table benutzer add constraint fk_benutzer_studio_1 foreign key (studio_id) references studio (id) on delete restrict on update restrict;
create index ix_benutzer_studio_1 on benutzer (studio_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table benutzer;

drop table studio;

drop table trainingsplan;

drop table uebung;

SET FOREIGN_KEY_CHECKS=1;

