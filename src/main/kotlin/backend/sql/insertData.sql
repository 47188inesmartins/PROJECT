BEGIN;

-- Inserção na tabela COMPANY
INSERT INTO COMPANY (nif, address, name, type, description)
VALUES ('123456789', 'Rua A', 'Empresa A', 'Tipo A', 'Descrição A');

INSERT INTO COMPANY (nif, address, name, type, description)
VALUES ('987654321', 'Rua B', 'Empresa B', 'Tipo B', 'Descrição B');

-- Inserção na tabela SCH_USER
INSERT INTO SCH_USER (email, password, name, birthday, availability, max_number_people)
VALUES ('usuario1@example.com', '71c72e7e33d58c8a0cab0caba274eef284c3c6c41d2780273d57a2a79d01754f', 'Usuário 1', '1990-01-01', 'AVAILABLE', 2);

INSERT INTO SCH_USER (email, password, name, birthday, availability, max_number_people)
VALUES ('usuario2@example.com', '71c72e7e33d58c8a0cab0caba274eef284c3c6c41d2780273d57a2a79d01754f', 'Usuário 2', '1995-02-02', 'UNAVAILABLE', 1);

-- Inserção na tabela USER_COMPANY
INSERT INTO USER_COMPANY (user_id, company_id, role)
VALUES (3,7, 'EMPLOYEE');

INSERT INTO USER_COMPANY (user_id, company_id, role)
VALUES (4, 7, 'MANAGER');

-- Inserção na tabela U_ROLE (já foram inseridos os valores 'GUEST', 'CLIENT', 'MANAGER' e 'EMPLOYEE' na criação da tabela)

-- Inserção na tabela USER_ROLE
INSERT INTO USER_ROLE (role_name, user_id)
VALUES ('EMPLOYEE', 3);

INSERT INTO USER_ROLE (role_name, user_id)
VALUES ('MANAGER', 4);

-- Inserção na tabela SCHEDULE
INSERT INTO SCHEDULE (company_id, between_interval)
VALUES (7, '00:30:00');

INSERT INTO SCHEDULE (company_id, between_interval)
VALUES (8, '00:30:00');

-- Inserção na tabela UNAVAILABILITY
INSERT INTO UNAVAILABILITY (date_begin, date_end, hour_begin, hour_end, user_id)
VALUES ('2023-01-01', '2023-01-05', '10:00', '12:00', 1);

INSERT INTO UNAVAILABILITY (date_begin, date_end, hour_begin, hour_end, user_id)
VALUES ('2023-02-01', '2023-02-05', '14:00', '16:00', 2);

-- Inserção na tabela SERVICE
INSERT INTO SERVICE (service_name, duration, number_max, price, company_id)
VALUES ('Serviço A', '01:30:00', 2, 50.00, 7);

INSERT INTO SERVICE (service_name, duration, number_max, price, company_id)
VALUES ('Serviço B', '01:00:00', 1, 30.00, 7);

-- Inserção na tabela APPOINTMENT
INSERT INTO APPOINTMENT (app_hour, app_date, schedule_id, service_id)
VALUES ('10:00', '2023-01-10', 1, 1);

INSERT INTO APPOINTMENT (app_hour, app_date, schedule_id, service_id)
VALUES ('11:00', '2023-02-10', 2, 2);

-- Inserção na tabela APPOINTMENT_USER
INSERT INTO APPOINTMENT_USER (user_id, appointment_id)
VALUES (3, 1);

INSERT INTO APPOINTMENT_USER (user_id, appointment_id)
VALUES (3, 2);

-- Inserção na tabela USER_SERVICE
INSERT INTO USER_SERVICE (user_id, service_id)
VALUES (3, 1);

INSERT INTO USER_SERVICE (user_id, service_id)
VALUES (3, 2);

-- Inserção na tabela SCH_DAY
INSERT INTO SCH_DAY (begin_hour, end_hour, interval_begin, interval_end, week_days, schedule_id)
VALUES ('08:00', '18:00', '08:00', '09:00', 'MON', 1);

INSERT INTO SCH_DAY (begin_hour, end_hour, interval_begin, interval_end, week_days, schedule_id)
VALUES ('09:00', '17:00', '09:00', '10:00', 'TUE', 2);

-- Inserção na tabela SERVICE_DAY
INSERT INTO SERVICE_DAY (day_id, service_id)
VALUES (1, 1);

INSERT INTO SERVICE_DAY (day_id, service_id)
VALUES (2, 2);

-- Inserção na tabela VACATION
INSERT INTO VACATION (date_begin, date_end, schedule_id)
VALUES ('2023-03-01', '2023-03-07', 1);

INSERT INTO VACATION (date_begin, date_end, schedule_id)
VALUES ('2023-04-01', '2023-04-07', 2);

COMMIT;


select s.id,s.service_name,s.duration,s.number_max,s.price,s.company_id
            from service s inner join SERVICE_DAY d on s.id = d.service_id inner join sch_day sd on sd.id = d.day_id and sd.week_days = :weekDay
            and (:beginHour between sd.begin_hour and interval_begin or :beginHour between  interval_end and sd.end_hour)
            and (TIME :beginHour + (s.duration || ' minutes')::INTERVAL between sd.begin_hour and interval_begin
            or TIME :beginHour + (s.duration || ' minutes')::INTERVAL between  interval_end and sd.end_hour)
            and s.company_id = :companyId;


SELECT *
FROM COMPANY
WHERE type = ANY(ARRAY['beauty', 'LIFESTYLE', 'fitness']);

SELECT id
FROM company c
WHERE LOWER(c.name) COLLATE utf8_general_ci LIKE '%inspecoes%'
   OR c.description COLLATE utf8_general_ci LIKE '%kjasndjksndkj%'
   OR c.type COLLATE utf8_general_ci LIKE '%fit%';


SELECT * FROM table WHERE lower(unaccent(table.id)) = lower('Jose')