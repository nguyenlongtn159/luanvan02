# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table danh_gia (
  id                        bigint not null,
  time                      varchar(255),
  decription                varchar(255),
  tieude                    varchar(255),
  user_id                   bigint,
  userid                    varchar(255),
  diem                      integer,
  constraint pk_danh_gia primary key (id))
;

create table detai (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_detai primary key (id))
;

create table message (
  id                        bigint not null,
  tieude                    varchar(255),
  noidung                   varchar(255),
  email                     varchar(255),
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
  ok                        boolean,
  khoa                      integer,
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
create sequence danh_gia_seq;

create sequence detai_seq;

create sequence message_seq;

create sequence tag_seq;

create sequence user_account_seq;

alter table danh_gia add constraint fk_danh_gia_user_1 foreign key (user_id) references user_account (id) on delete restrict on update restrict;
create index ix_danh_gia_user_1 on danh_gia (user_id);
alter table user_account add constraint fk_user_account_tag_2 foreign key (tag_id) references tag (id) on delete restrict on update restrict;
create index ix_user_account_tag_2 on user_account (tag_id);
alter table user_account add constraint fk_user_account_detai_3 foreign key (detai_id) references detai (id) on delete restrict on update restrict;
create index ix_user_account_detai_3 on user_account (detai_id);
alter table user_account add constraint fk_user_account_chuyenmon_4 foreign key (chuyenmon_id) references detai (id) on delete restrict on update restrict;
create index ix_user_account_chuyenmon_4 on user_account (chuyenmon_id);
alter table user_account add constraint fk_user_account_duochuongdan_5 foreign key (duochuongdan_id) references user_account (id) on delete restrict on update restrict;
create index ix_user_account_duochuongdan_5 on user_account (duochuongdan_id);



alter table user_account_message add constraint fk_user_account_message_user__01 foreign key (user_account_id) references user_account (id) on delete restrict on update restrict;

alter table user_account_message add constraint fk_user_account_message_messa_02 foreign key (message_id) references message (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists danh_gia;

drop table if exists detai;

drop table if exists message;

drop table if exists user_account_message;

drop table if exists tag;

drop table if exists user_account;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists danh_gia_seq;

drop sequence if exists detai_seq;

drop sequence if exists message_seq;

drop sequence if exists tag_seq;

drop sequence if exists user_account_seq;

