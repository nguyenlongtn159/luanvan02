# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table detai (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_detai primary key (id))
;

create table message (
  id                        bigint not null,
  tieude                    varchar(255),
  noidung                   varchar(255),
  nguoigui                  varchar(255),
  ngaygui                   varchar(255),
  constraint pk_message primary key (id))
;

create table tag (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_tag primary key (id))
;

create table user_account (
  id                        bigint not null,
  email                     varchar(255),
  password                  varchar(255),
  description               varchar(255),
  name                      varchar(255),
  tag_id                    bigint,
  chucvu                    bigint,
  dieukien                  boolean,
  detai_id                  bigint,
  chuyenmon_id              bigint,
  cmon                      bigint,
  duochuongdan_id           bigint,
  date                      timestamp,
  sdt                       integer,
  chucdanh                  varchar(255),
  noicongtac                varchar(255),
  constraint pk_user_account primary key (id))
;


create table user_account_message (
  user_account_id                bigint not null,
  message_id                     bigint not null,
  constraint pk_user_account_message primary key (user_account_id, message_id))
;
create sequence detai_seq;

create sequence message_seq;

create sequence tag_seq;

create sequence user_account_seq;

alter table user_account add constraint fk_user_account_tag_1 foreign key (tag_id) references tag (id) on delete restrict on update restrict;
create index ix_user_account_tag_1 on user_account (tag_id);
alter table user_account add constraint fk_user_account_detai_2 foreign key (detai_id) references detai (id) on delete restrict on update restrict;
create index ix_user_account_detai_2 on user_account (detai_id);
alter table user_account add constraint fk_user_account_chuyenmon_3 foreign key (chuyenmon_id) references detai (id) on delete restrict on update restrict;
create index ix_user_account_chuyenmon_3 on user_account (chuyenmon_id);
alter table user_account add constraint fk_user_account_duochuongdan_4 foreign key (duochuongdan_id) references user_account (id) on delete restrict on update restrict;
create index ix_user_account_duochuongdan_4 on user_account (duochuongdan_id);



alter table user_account_message add constraint fk_user_account_message_user__01 foreign key (user_account_id) references user_account (id) on delete restrict on update restrict;

alter table user_account_message add constraint fk_user_account_message_messa_02 foreign key (message_id) references message (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists detai;

drop table if exists message;

drop table if exists user_account_message;

drop table if exists tag;

drop table if exists user_account;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists detai_seq;

drop sequence if exists message_seq;

drop sequence if exists tag_seq;

drop sequence if exists user_account_seq;

