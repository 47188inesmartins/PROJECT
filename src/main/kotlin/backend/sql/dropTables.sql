BEGIN;

drop table if exists COMPANY cascade;
drop table if exists CLIENT cascade;
drop table if exists SCHEDULE cascade;
drop table if exists APPOINTMENT cascade;
drop table if exists SERVICE cascade;
drop table if exists U_ROLE cascade;
drop table if exists USER_ROLE cascade;
drop table if exists SCH_USER cascade;
drop table if exists COMPANY_NUMBERS cascade;
drop table if exists SCH_DAY cascade;
drop table if exists EMPLOYEE cascade;
drop table if exists EMPLOYEE_SERVICE cascade;
drop table if exists MANAGER cascade;
drop table if exists VACATION cascade;
drop table if exists CALENDAR cascade;
drop table if exists SERVICE_APPOINTMENT cascade;
drop table if exists USER_SERVICE cascade;
drop table if exists APP_USER cascade;

COMMIT;