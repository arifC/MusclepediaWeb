# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table exercise (
  exercise_id               integer auto_increment not null,
  name                      varchar(255),
  beschreibung              varchar(255),
  schwierigkeit             varchar(255),
  constraint pk_exercise primary key (exercise_id))
;

create table plan (
  plan_id                   varchar(40) not null,
  type                      varchar(255),
  constraint pk_plan primary key (plan_id))
;

create table rating (
  rating_id                 varchar(40) not null,
  studio_studio_id          varchar(40) not null,
  facilities                double,
  service                   double,
  price                     double,
  location                  double,
  constraint pk_rating primary key (rating_id))
;

create table studio (
  studio_id                 varchar(40) not null,
  plz                       varchar(255),
  ort                       varchar(255),
  strasse                   varchar(255),
  name                      varchar(255),
  total_rating              double,
  total_facilities          double,
  total_location            double,
  total_price               double,
  total_service             double,
  constraint pk_studio primary key (studio_id))
;

create table user (
  benutzer_id               varchar(40) not null,
  name                      varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  studio_studio_id          varchar(40),
  my_plan_plan_id           varchar(40),
  constraint pk_user primary key (benutzer_id))
;

create table weight (
  weight_id                 varchar(40) not null,
  weight                    double,
  date                      datetime,
  date_text                 varchar(255),
  constraint pk_weight primary key (weight_id))
;


create table plan_exercise (
  plan_plan_id                   varchar(40) not null,
  exercise_exercise_id           integer not null,
  constraint pk_plan_exercise primary key (plan_plan_id, exercise_exercise_id))
;
alter table rating add constraint fk_rating_studio_1 foreign key (studio_studio_id) references studio (studio_id) on delete restrict on update restrict;
create index ix_rating_studio_1 on rating (studio_studio_id);
alter table user add constraint fk_user_studio_2 foreign key (studio_studio_id) references studio (studio_id) on delete restrict on update restrict;
create index ix_user_studio_2 on user (studio_studio_id);
alter table user add constraint fk_user_myPlan_3 foreign key (my_plan_plan_id) references plan (plan_id) on delete restrict on update restrict;
create index ix_user_myPlan_3 on user (my_plan_plan_id);



alter table plan_exercise add constraint fk_plan_exercise_plan_01 foreign key (plan_plan_id) references plan (plan_id) on delete restrict on update restrict;

alter table plan_exercise add constraint fk_plan_exercise_exercise_02 foreign key (exercise_exercise_id) references exercise (exercise_id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table exercise;

drop table plan_exercise;

drop table plan;

drop table rating;

drop table studio;

drop table user;

drop table weight;

SET FOREIGN_KEY_CHECKS=1;

