BEGIN;

drop table if exists COMPANY cascade;
drop table if exists dbo.CLIENT cascade;
drop table if exists dbo.SCHEDULE cascade;
drop table if exists dbo.APPOINTMENT cascade;
drop table if exists dbo.SERVICE cascade;
drop table if exists dbo.ROLE cascade;
drop table if exists dbo.USER_ROLE cascade;
drop table if exists dbo.USER cascade;
drop table if exists dbo.COMPANY_NUMBERS cascade;
drop table if exists dbo.DAY cascade;
drop table if exists dbo.EMPLOYEE cascade;
drop table if exists dbo.EMPLOYEE_SERVICE cascade;
drop table if exists dbo.MANAGER cascade;
drop table if exists dbo.VACATION cascade;
drop table if exists dbo.calendar cascade;
COMMIT;