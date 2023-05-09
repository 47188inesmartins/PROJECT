BEGIN;

create schema if not exists public;

CREATE EXTENSION IF NOT EXISTS pgcrypto;

create table if not exists COMPANY (
   id serial primary key,
   nif varchar(9) unique,
   address varchar(40),
   name varchar(20),
   type varchar(30),
   description varchar(300)
);

create table if not exists COMPANY_NUMBERS(
    phone_number varchar(13),
    company_id int,
    primary key (phone_number),
    foreign key (company_id) references COMPANY(id)
);

create table if not exists SCH_USER (
    id serial primary key,
    token UUID unique,
    email varchar(50) unique CHECK (email LIKE '%@%'),
    password varchar(250),
    name varchar(200),
    birthday date check (date(CURRENT_TIMESTAMP) >= birthday + interval '18 year'),
    availability varchar(15) not null default 'NONE' check (availability in ('AVAILABLE','UNAVAILABLE','NONE')),
    max_number_people int default 1,
    company_id int,
    foreign key(company_id) references COMPANY(id)
);


create table if not exists U_ROLE (
    name varchar(20) primary key CHECK ( name IN ('GUEST', 'CLIENT', 'EMPLOYEE', 'MANAGER'))
);



create table if not exists USER_ROLE(
    role_name varchar(20),
    user_id int,
    primary key (role_name, user_id),
    foreign key (role_name) references U_ROLE(name),
    foreign key (user_id) references SCH_USER(id)
);


create table if not exists SCHEDULE (
    id serial primary key,
    company_id int not null,
    between_interval Time,
    foreign key(company_id) references COMPANY(id)
);


create table if not exists UNAVAILABILITY(
    id serial primary key,
    date_begin date,
    date_end date,
    hour_begin time,
    hour_end time,
    user_id int,
    foreign key (user_id) references SCH_USER (id)
);


create table if not exists SERVICE (
    id serial primary key,
    service_name varchar(30),
    duration time,
    number_max int default 1,
    price float,
    company_id int,
    foreign key(company_id) references COMPANY(id)
);

create table if not exists APPOINTMENT (
    id serial primary key,
    app_hour time,
    app_date date,
    schedule_id int,
    service_id int,
    employee_id int,
    user_id int,
    foreign key(service_id) references SERVICE(id),
    foreign key(schedule_id) references SCHEDULE(id),
    foreign key(user_id) references SCH_USER(id),
    foreign key(employee_id) references SCH_USER(id)
);


create table if not exists USER_SERVICE(
    user_id int,
    service_id int,
    primary key(user_id, service_id),
    foreign key (user_id) references SCH_USER(id),
    foreign key (service_id) references SERVICE(id)
);

create table if not exists SCH_DAY(
     id serial primary key,
     begin_hour time,
     end_hour time,
     interval_begin time,
     interval_end time,
     week_days char(4) CHECK (week_days in ('MON','TUE','WED','THU','FRI','SAT','SUN')),
     schedule_id int,
     service_id int,
     CONSTRAINT uc_sch_day UNIQUE (week_days, schedule_id),
     CONSTRAINT uc_serv_day UNIQUE (week_days, service_id),
     foreign key (schedule_id) references SCHEDULE(id),
     foreign key (service_id) references SERVICE(id)
);

create table if not exists VACATION(
    id serial primary key,
    date_begin date,
    date_end date check ( date_end > date_begin ),
    schedule_id int,
    foreign key (schedule_id) references SCHEDULE(id)
);


insert into u_role (name) values ('GUEST');
insert into u_role (name) values ('CLIENT');
insert into u_role (name) values ('MANAGER');
insert into u_role (name) values ('EMPLOYEE');



COMMIT;

rollback;