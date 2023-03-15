BEGIN;

drop table if exists dbo.COMPANY cascade;
drop table if exists dbo.CLIENT cascade;
drop table if exists dbo.SCHEDULE cascade;
drop table if exists dbo.APPOINTMENT cascade;
drop table if exists dbo.SERVICE cascade;
drop table if exists dbo.CALENDAR cascade;

COMMIT;