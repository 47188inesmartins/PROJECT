BEGIN;

create schema if not exists dbo;

create table if not exists dbo.COMPANY (
   id serial primary key,
   nif varchar(9) unique,
   phone_number varchar(13) unique,
   address varchar(40),
   comp_name varchar(20),
   comp_type varchar(30),
   description varchar(300)
);


create table if not exists dbo.USER (
    id serial primary key,
    token UUID unique default gen_random_uuid(),
    email varchar(50) unique CHECK (email LIKE '%@%'),
    password varchar(30),
    username varchar(30) unique,
    name varchar(30),
    birthday date check (date(CURRENT_TIMESTAMP) >= birthday + interval '18 year')
);

create table if not exists dbo.EMPLOYEE (
    id serial primary key,
    token UUID unique default gen_random_uuid(),
    email varchar(50) unique CHECK (email LIKE '%@%'),
    password varchar(30),
    username varchar(30) unique,
    name varchar(30),
    birthday date check (date(CURRENT_TIMESTAMP) >= birthday + interval '18 year')
);

create table if not exists dbo.MANAGER (
    id serial primary key,
    token UUID unique default gen_random_uuid(),
    email varchar(50) unique CHECK (email LIKE '%@%'),
    password varchar(30),
    username varchar(30) unique,
    name varchar(30),
    birthday date check (date(CURRENT_TIMESTAMP) >= birthday + interval '18 year'),
    comp_id int,
    foreign key(comp_id) references dbo.COMPANY(id)
);

create table if not exists dbo.CLIENT (
   id serial primary key,
   token UUID unique default gen_random_uuid(),
   email varchar(50) unique CHECK (email LIKE '%@%'),
   password varchar(30),
   username varchar(30) unique,
   name varchar(30),
   birthday date check (date(CURRENT_TIMESTAMP) >= birthday + interval '18 year')
);

create table if not exists dbo.ROLE (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(20) NOT NULL UNIQUE CHECK ( name IN ('guest', 'user', 'employee', 'manager') )
);

/*

N:N relation with user and role

*/

create table if not exists dbo.USER_ROLE(
    role_id int,
    user_id int,
    primary key(role_id,user_id),
    foreign key (role_id) references dbo.ROLE(id),
    foreign key (user_id) references dbo.USER(id)
);

create table if not exists dbo.SCHEDULE (
    id serial primary key,
    begin_hour time,
    end_hour time,
    shc_interval time,
    duration time,
    comp_id int,
    week_days varchar(27) CHECK (week_days like SCHEDULE.week_days ~ '^(seg|ter|qua|qui|sex|sab|dom)(;(seg|ter|qua|qui|sex|sab|dom)){0,6}$'),
    foreign key(comp_id) references dbo.COMPANY(id)
);

create table if not exists dbo.APPOINTMENT (
    id serial primary key,
    number_app_people int,
    app_hour time,
    availability varchar(11) check (availability like 'available' or availability like 'unavailable'),
    sid int,
    cid int,
    foreign key(sid) references dbo.SCHEDULE(id),
    foreign key(cid) references dbo.CLIENT(id)
);

create table if not exists dbo.SERVICE (
    id serial primary key,
    service_name varchar(30),
    duration time,
    number_max int default 1,
    price float,
    cid int,
    aid int,
    foreign key(cid) references dbo.COMPANY(id),
    foreign key(aid) references dbo.APPOINTMENT(id)
);


/*

N:N relation with employee and service

*/

create table if not exists dbo.EMPLOYEE_SERVICE(
    employee_id int,
    service_id int,
    primary key(employee_id, service_id),
    foreign key (employee_id) references dbo.EMPLOYEE(id),
    foreign key (service_id) references dbo.SERVICE(id)
);



COMMIT;