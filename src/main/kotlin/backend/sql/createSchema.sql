BEGIN;

/*create schema if not exists dbo;*/

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
    cid int,
    primary key (phone_number),
    foreign key (cid) references COMPANY(id)
);


create table if not exists SCH_USER (
    id serial primary key,
    token UUID unique /*default gen_random_uuid()*/,
    email varchar(50) unique CHECK (email LIKE '%@%'),
    password varchar(30),
    name varchar(30),
    birthday date check (date(CURRENT_TIMESTAMP) >= birthday + interval '18 year')
);

create table if not exists EMPLOYEE (
    id serial primary key,
    token UUID unique /*default gen_random_uuid()*/,
    email varchar(50) unique CHECK (email LIKE '%@%'),
    password varchar(30),
    username varchar(30) unique,
    name varchar(30),
    birthday date check (date(CURRENT_TIMESTAMP) >= birthday + interval '18 year'),
    availability varchar(15) check (availability in ('available','unavailable'))
);

create table if not exists MANAGER (
    id serial primary key,
    token varchar(20) unique /*default gen_random_uuid()*/,
    email varchar(50) unique CHECK (email LIKE '%@%'),
    password varchar(30),
    name varchar(30),
    birthday date check (date(CURRENT_TIMESTAMP) >= birthday + interval '18 year'),
    comp_id int,
    foreign key(comp_id) references COMPANY(id)
);

create table if not exists CLIENT (
   id serial primary key,
   token UUID unique default gen_random_uuid(),
   email varchar(50) unique CHECK (email LIKE '%@%'),
   password varchar(30),
   name varchar(30),
   birthday date check (date(CURRENT_TIMESTAMP) >= birthday + interval '18 year')
);

create table if not exists U_ROLE (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(20) NOT NULL UNIQUE CHECK ( name IN ('guest', 'user', 'employee', 'manager') )
);


/*N:N relation with user and role*/
create table if not exists USER_ROLE(
    role_id int,
    user_id int,
    primary key (role_id, user_id),
    foreign key (role_id) references U_ROLE(id),
    foreign key (user_id) references SCH_USER(id)
);

create table if not exists SCHEDULE (
    id serial primary key,
    comp_id int,
    foreign key(comp_id) references COMPANY(id)
);

create table if not exists APPOINTMENT (
    id serial primary key,
    number_app_people int,
    app_hour time,
    availability varchar(11) check (availability like 'available' or availability like 'unavailable'),
    app_date date,
    sid int,
    cid int,
    eid int,
    foreign key(sid) references SCHEDULE(id),
    foreign key(cid) references CLIENT(id),
    foreign key(eid) references EMPLOYEE(id)
);

create table if not exists SERVICE (
    id serial primary key,
    service_name varchar(30),
    duration time,
    number_max int default 1,
    price float,
    cid int,
    foreign key(cid) references COMPANY(id)
);

/*N:N relation with service and appointment*/
create table if not exists SERVICE_APPOINTMENT(
    service_id int,
    appointment_id int,
    primary key (service_id,appointment_id),
    foreign key (service_id) references SERVICE(id),
    foreign key (appointment_id) references APPOINTMENT(id)
);


/*N:N relation with employee and service*/
create table if not exists EMPLOYEE_SERVICE(
    employee_id int,
    service_id int,
    primary key(employee_id, service_id),
    foreign key (employee_id) references EMPLOYEE(id),
    foreign key (service_id) references SERVICE(id)
);

create table if not exists SCH_DAY(
     id serial primary key,
     begin_hour time,
     end_hour time,
     day_interval time,
     week_days char(4) CHECK (week_days in ('MON','TUE','WED','THU','FRI','SAT','SUN')),
     sid int,
     foreign key (sid) references SCHEDULE(id)
);

create table if not exists VACATION(
    id serial primary key,
    date_begin date,
    date_end date check ( date_end > date_begin ),
    sid int,
    foreign key (sid) references SCHEDULE(id)
);

COMMIT;

