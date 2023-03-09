
create schema if not exists dbo;

create table if not exists dbo.EMPRESA (
    email varchar(100) primary key,
    password varchar(100),
    tipo varchar(30),
    descricao varchar(300),
    beginhour timestamp,
    endhour timestamp,
    intervalo timestamp
 );

create table if not exists dbo.CLIENTE (
    email varchar(100) primary key,
    password varchar(100),
    username varchar(30),
    firstname varchar(20),
    lastname varchar(20),
    birthday timestamp
);

create table if not exists dbo.MARCACOES (
    id serial primary key,
    client varchar(100),
    emp varchar(100),
    date timestamp,
    hour timestamp,
    foreign key(client) references dbo.CLIENTE(email),
    foreign key(emp) references dbo.EMPRESA(email)
);