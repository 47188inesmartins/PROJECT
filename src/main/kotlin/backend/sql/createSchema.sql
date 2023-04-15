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


/*
    company's composed attribute
*/
create table if not exists COMPANY_NUMBERS(
    phone_number varchar(13),
    company_id int,
    primary key (phone_number),
    foreign key (company_id) references COMPANY(id)
);

/*
create table if not exists SCH_USER (
    id serial primary key,
    token UUID unique /*default gen_random_uuid()*/,
    email varchar(50) unique CHECK (email LIKE '%@%'),
    password varchar(30),
    name varchar(30),
    birthday date check (date(CURRENT_TIMESTAMP) >= birthday + interval '18 year')
);*/

create table if not exists SCH_USER (
    id serial primary key,
    token UUID unique,
    email varchar(50) unique CHECK (email LIKE '%@%'),
    password varchar(250),
    name varchar(200),
    birthday date check (date(CURRENT_TIMESTAMP) >= birthday + interval '18 year'),
    availability varchar(15) not null default 'none' check (availability in ('available','unavailable','none')),
    max_number_people int default 1,
    company_id int,
    foreign key(company_id) references COMPANY(id)
);


create table if not exists U_ROLE (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id int,
    foreign key(user_id) references SCH_USER(id),
    name varchar(20) NOT NULL UNIQUE CHECK ( name IN ('guest', 'user', 'employee', 'manager') )
);


/*N:N relation with user and role
create table if not exists USER_ROLE(
    role_id int,
    user_id int,
    primary key (role_id, user_id),
    foreign key (role_id) references U_ROLE(id),
    foreign key (user_id) references SCH_USER(id)
);
*/

create table if not exists SCHEDULE (
    id serial primary key,
    company_id int unique,
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
  /*  number_app_people int default 1,*/
    company_id int,
    foreign key(company_id) references COMPANY(id)
);

create table if not exists APPOINTMENT (
    id serial primary key,
    app_hour time,
    app_date date,
    schedule_id int,
    service_id int,
    user_id int,
    foreign key(service_id) references SERVICE(id),
    foreign key(schedule_id) references SCHEDULE(id),
    foreign key(user_id) references SCH_USER(id)
);


create table if not exists APPOINTMENT_USER (
    user_id int,
    appointment_id int,
    primary key (user_id, appointment_id),
    foreign key(user_id) references SCH_USER(id),
    foreign key(appointment_id) references APPOINTMENT(id)
);


/*N:N relation with service and appointment
create table if not exists SERVICE_APPOINTMENT(
    service_id int,
    appointment_id int,
    availability varchar(11) default 'available' check (availability like 'available' or availability like 'unavailable'),
    primary key (service_id,appointment_id),
    foreign key (service_id) references SERVICE(id),
    foreign key (appointment_id) references APPOINTMENT(id)
);
*/


/*N:N relation with employee and service*/
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
     day_interval time,
     week_days char(4) CHECK (week_days in ('MON','TUE','WED','THU','FRI','SAT','SUN')),
     schedule_id int,
     foreign key (schedule_id) references SCHEDULE(id)
);


create table if not exists SERVICE_DAY(
     days_id int,
     service_id int,
     begin_hour time,
     end_hour time,
     primary key (days_id, service_id),
     foreign key (days_id) references SCH_DAY(id),
     foreign key (service_id) references SERVICE(id)
);

create table if not exists VACATION(
    id serial primary key,
    date_begin date,
    date_end date check ( date_end > date_begin ),
    schedule_id int,
    foreign key (schedule_id) references SCHEDULE(id)
);


COMMIT;

rollback;