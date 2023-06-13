BEGIN;

delete from SCHEDULE cascade;
delete from COMPANY cascade;
delete from APPOINTMENT cascade;
delete from SERVICE cascade;
delete from U_ROLE cascade;
delete from SCH_USER cascade;
delete from COMPANY_NUMBERS cascade;
delete from SCH_DAY cascade;
delete from VACATION cascade;
delete from USER_SERVICE cascade;

COMMIT;

