package backend.jvm.repository

import backend.jvm.model.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.sql.Time
import java.sql.Date

interface AppointmentRepository: JpaRepository<Appointment, Int>{

    @Query(value = "select * from appointment where schedule_id = (select id from schedule s where s.company_id = :id)", nativeQuery = true)
    fun getAllOnGoingAppointments (@Param("id")id:Int): List<Appointment>

    @Query(value = "select * from appointment a where a.schedule_id=:sid and a.app_date=:date and a.app_hour=:hour", nativeQuery = true)
    fun getAppointmentByDateAndHour (@Param("sid")id:Int, @Param("date")date:Date, @Param("hour")hour:Time): List<Appointment>

    @Query(value = "select * from appointment a inner join appointment_user au on a.id = au.appointment_id and au.user_id =:id", nativeQuery = true)
    fun getAppointmentByUserDB(@Param("id") id: Int): List<Appointment>

    @Query(value = "select * from APPOINTMENT where APPOINTMENT.schedule_id = :schedule ", nativeQuery = true)
    fun getAppointmentsBySchedule(@Param("schedule") schedule: Int):List<Appointment>

    @Modifying
    @Query(value = "delete * from APPOINTMENT a where exist ( select * from  appointment_user au where " +
            "a.id = au.appointment_id and au.user_id =:user) and a.app_hour > :hour and a.app_date > :date  ", nativeQuery = true)
    fun deleteAppointmentByDateAndEmployee(@Param("user") user: Int, @Param("hour") hour: Time,@Param("date") date: Date )

}