BEGIN;

create schema if not exists dbo;
 id serial primary key,
    token UUID unique default gen_random_uuid(),
    email varchar(50) unique CHECK (email LIKE '%@%'),
    password varchar(30),
    username varchar(20) unique,
    comp_name varchar(20),
    comp_type varchar(30),
    description varchar(300)
create table if not exists dbo.COMPANY (

);

create table if not exists dbo.CLIENT (
    id serial primary key,
    token UUID unique default gen_random_uuid(),
    email varchar(50) unique CHECK (email LIKE '%@%'),
    password varchar(30),
    username varchar(30) unique,
    cli_name varchar(30),
    birthday date check (date(CURRENT_TIMESTAMP) >= birthday + interval '18 year')
);


create table if not exists dbo.SCHEDULE (
    id serial primary key,
    begin_hour time,
    end_hour time,
    shc_interval time,
    duration time,
    comp_id int,
    foreign key(comp_id) references dbo.COMPANY(id)
);

create table if not exists dbo.APPOINTMENT (
    id serial primary key,
    app_number int,
    app_hour varchar(100),
    availability varchar(12) check (availability like 'available' or availability like 'unavailable'),
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
    cid int,
    aid int,
    foreign key(cid) references dbo.COMPANY(id),
    foreign key(aid) references dbo.APPOINTMENT(id)
);

create table if not exists dbo.CALENDAR (
    id serial,
    cid int,
    week_day varchar(15) check (week_day in ('Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday')),
    calendar_state varchar(10) check (calendar_state in ('open','close')),
    primary key(id,cid),
    foreign key(cid) references dbo.COMPANY(id)
);

COMMIT;