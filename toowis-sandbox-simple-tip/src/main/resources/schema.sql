drop table if exists car;

create table car (
      id    bigint serial not null
    , maker varchar(255)  not null
    , model varchar(255)  not null
    , year  int           not null
)
