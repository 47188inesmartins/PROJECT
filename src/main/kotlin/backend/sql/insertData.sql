insert into company(nif, address, name, type, description)
values ('12345679', 'Rua Violinos1', 'PT1', 'Treino11', 'treinos com o pt');
values ('12345678', 'Rua arcos', 'Salao de beleza', 'Salao de beleza', 'cabelos,unhas e tudo');

insert into company_numbers(phone_number, cid)
values('218536543',1);
values('961234567',1);

insert into SCH_USER(token,email,password,name,birthday)
values('4f298735-5893-4199-a179-3af3fabc38b8','user@gmail.com','senha_segura','user','2001-01-01');

insert into EMPLOYEE(token,email,password,username,name,birthday,availability)
values('4f298735-5893-4199-a179-3af3fabc38b7','user@gmail.com','1111','user','user','2001-01-01','available');

insert into MANAGER(token,email,password,name,birthday,comp_id)
values('4f298735-5893-4199-a179-3af3fabc38b9','user@gmail.com','1111','user','2001-01-01',1);

insert into CLIENT(token,email,password,name,birthday)
values('4f298735-5893-4199-a179-3af3fabc38b0','user@gmail.com','1111','user','2001-01-01');

insert into u_role(name)
values('user');

insert into SCHEDULE(comp_id)
values(1);

insert into APPOINTMENT (number_app_people, app_hour, availability, app_date, sid, cid, eid)
values(1,'12:00','available','2023-11-11',1,1,1);


insert into SERVICE (service_name, duration, number_max, price, cid)
values ('Corrida','00:40:00',2,12.3,1);

insert into SERVICE_APPOINTMENT (service_id, appointment_id)
values (1,2);

insert into employee_service (employee_id, service_id)
values (1,1);

insert into SCH_DAY(begin_hour,end_hour ,day_interval , week_days, sid)
values ('9:30:00','17:30:00','01:30:00','MON',1);

insert into vacation(date_begin,date_end,sid)
values ('2023-11-11','2023-11-17',1);