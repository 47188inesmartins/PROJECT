-- Inserção na tabela COMPANY
INSERT INTO COMPANY (nif, address, name, type, description)
VALUES ('123456789', 'Rua A', 'Empresa 1', 'Tipo 1', 'Descrição 1');

-- Inserção na tabela COMPANY_NUMBERS
INSERT INTO COMPANY_NUMBERS (phone_number, company_id)
VALUES ('123456789', 1);

-- Inserção na tabela SCH_USER
INSERT INTO SCH_USER (token, email, password, name, birthday, availability, max_number_people)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'user@example.com', 'password123', 'John Doe', '2000-01-01', 'AVAILABLE', 1);

-- Inserção na tabela USER_COMPANY
INSERT INTO USER_COMPANY (user_id, company_id, role)
VALUES (1, 1, 'MANAGER');

-- Inserção na tabela U_ROLE
INSERT INTO U_ROLE (name)
VALUES ('MANAGER');

-- Inserção na tabela SCHEDULE
INSERT INTO SCHEDULE (company_id, between_interval)
VALUES (1, '08:00:00 - 18:00:00');

-- Inserção na tabela UNAVAILABILITY
INSERT INTO UNAVAILABILITY (date_begin, date_end, hour_begin, hour_end, user_id)
VALUES ('2023-06-10', '2023-06-15', '09:00:00', '12:00:00', 1);

-- Inserção na tabela SERVICE
INSERT INTO SERVICE (service_name, duration, number_max, price, company_id)
VALUES ('Serviço 1', '01:30:00', 1, 50.00, 1);

-- Inserção na tabela APPOINTMENT
INSERT INTO APPOINTMENT (app_hour, app_date, schedule_id, service_id)
VALUES ('10:00:00', '2023-06-10', 1, 1);

-- Inserção na tabela APPOINTMENT_USER
INSERT INTO APPOINTMENT_USER (user_id, appointment_id)
VALUES (1, 1);

-- Inserção na tabela USER_SERVICE
INSERT INTO USER_SERVICE (user_id, service_id)
VALUES (1, 1);

-- Inserção na tabela SCH_DAY
INSERT INTO SCH_DAY (begin_hour, end_hour, interval_begin, interval_end, week_days, schedule_id, service_id)
VALUES ('08:00:00', '18:00:00', NULL, NULL, 'MON', 1, 1);

-- Inserção na tabela SERVICE_DAY
INSERT INTO SERVICE_DAY (day_id, service_id)
VALUES (1, 1);

-- Inserção na tabela VACATION
INSERT INTO VACATION (date_begin, date_end, schedule_id)
VALUES ('2023-07-01', '2023-07-15', 1);