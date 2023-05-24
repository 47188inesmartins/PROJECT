insert into company(nif, address, name, type, description)
values ('12345679', 'Rua Violinos1', 'PT1', 'Treino11', 'treinos com o pt');
values ('12345678', 'Rua arcos', 'Salao de beleza', 'Salao de beleza', 'cabelos,unhas e tudo');

insert into company_numbers(phone_number, company_id)
values('218536543',1);
values('961234567',1);

insert into SCH_USER(token,email,password,name,birthday,availability,company_id)
values('4f298735-5893-4199-a179-3af3fabc38b8','user@gmail.com','senha_segura','user','2001-01-01','available',1);


select * from service s inner join sch_day sd on s.id = sd.service_id and sd.week_days = 'MON'
and  ('9:00:00'  between sd.begin_hour and interval_begin or '9:00:00' between  interval_end and sd.end_hour)
and (TIME '9:00:00' + (s.duration || ' minutes')::INTERVAL between sd.begin_hour and interval_begin
or TIME '9:00:00' + (s.duration || ' minutes')::INTERVAL between  interval_end and sd.end_hour) and s.company_id = 1
inner join user_service us on s.id = us.service_id
inner join unavailability u on us.user_id = u.user_id
where u.user_id not in
(select user_id from unavailability where (date_end is null and date_begin ='2001-01-01'
                                         and ((hour_begin  >= '9:00:00' + (s.duration || ' minutes')::INTERVAL and hour_begin < '9:00:00' + (s.duration || ' minutes')::INTERVAL )
                                         or (hour_end > '9:00:00' + (s.duration || ' minutes')::INTERVAL  and hour_end <= '9:00:00' + (s.duration || ' minutes')::INTERVAL )))
         or ('2001-01-01' <= date_begin and '2001-01-01' >= date_end))
;

SELECT * FROM sch_day where begin_hour < (TIME '01:30:00' + INTERVAL '00:45:00');



begin;
select * from sch_user where id in (select user_id from user_service where service_id = 1)
            and id not in (select user_id from unavailability where hour_begin between '10:00:00' and '11:00:00');
commit;



select * from sch_user where id in
            (select user_id from user_service where service_id = 1 and
            service_id in (select id from service where company_id = 1))
             and id not in (select user_id from unavailability where hour_begin between '12:00:00' and '13:00:00' and
            (date_begin <= '2001-06-01' and date_end >= '2001-06-1'));


select user_id from unavailability where hour_begin
    between '12:00:00' and '13:00:00' and
    (date_begin <= '2001-06-01' and date_end >= '2001-06-1');

begin;
insert into sch_user(email,password,name,birthday) values ('email@gmail.com', 'Email123@', 'Email', '2001-05-15');
commit;




select s.id, s.service_name, s.duration, s.number_max, s.price,
       s.company_id from service s inner join sch_day d on ( s.company_id = :companyId and
       s.id = d.service_id and d.week_days = 'MON' and (s.duration + interval '18:33:56' SECOND
           BETWEEN ) ));




select * from service s where s.company_id = :companyId and s.id in (select service_id from sch_day where week_days = :day);






select * from sch_user where id not in
(select user_id from unavailability where (date_end is null and date_begin ='2000-01-01'
                                        and ((hour_begin  >= '12:30:00' and hour_begin < '13:00:00')
                                        or (hour_end > '12:30:00' and hour_end <= '13:00:00')))
        or ('2000-01-01' <= date_begin and '2000-01-01' >= date_end))
and id in (select user_id from user_service where service_id = 1);


SELECT u.*
FROM sch_user u
         JOIN user_service us ON u.id = us.user_id
         LEFT JOIN (
    SELECT user_id
    FROM unavailability
    WHERE date_end IS NULL
        AND date_begin = '2000-01-01'
        AND (
                  (hour_begin >= '12:30:00' AND hour_begin < '14:00:00')
                  OR (hour_end >= '12:30:00' AND hour_end < '14:00:00')
              )
       OR ('2000-01-01' BETWEEN date_begin AND date_end)
) ua ON u.id = ua.user_id
WHERE ua.user_id IS NULL
  AND us.service_id = 1;



select * from service where id not in (select service_id from sch_day where )


SELECT *
FROM service s
WHERE s.company_id = 1
  AND s.id IN (
    SELECT service_id
    FROM sch_day
    WHERE week_days = 'TUE'
      AND '9:30:00' BETWEEN begin_hour AND end_hour
      AND ('9:30:00' + INTERVAL s.duration MINUTE) BETWEEN begin_hour AND end_hour
);


/*



*/

begin;
select * from appointment a where a.schedule_id = (select id from schedule s where s.company_id = 1) and a.app_date = '2001-06-11' and a.app_hour = '20:30:00';
commit;
rollback;

begin;
select * from sch_user where id in (select id from user_service where service_id = :idService);
commit;
rollback ;

select a.id,a.app_hour,a.app_date,a.schedule_id,a.service_id,a.user_id from appointment a inner join schedule s on s.company_id = 1;


select * from appointment a where a.schedule_id=1 and a.app_date='2001-06-11' and a.app_hour='21:31:34';



insert into u_role(name)
values('employee');

insert into SCHEDULE(company_id)
values(1);

insert into APPOINTMENT (app_hour, app_date, schedule_id, user_id)
values('12:00','2023-11-11',1,1);


insert into SERVICE (service_name, duration, number_max, price, company_id)
values ('Corrida','00:40:00',2,12.3,1);

insert into SCH_DAY(begin_hour,end_hour ,day_interval , week_days, schedule_id)
values ('9:30:00','17:30:00','01:30:00','MON',1);

insert into vacation(date_begin,date_end,schedule_id)
values ('2023-11-11','2023-11-17',1);

select * from service s where s.company_id = 1 and s.id in
             (select service_id from sch_day where week_days = 'MON' and '9:30:00' between begin_hour and end_hour
            and (begin_hour)as time + s.duration as time between begin_hour and end_hour)
