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
values('4f298735-5893-4199-a179-3af3fabc38b0','user@gmail.com','1111','user');

