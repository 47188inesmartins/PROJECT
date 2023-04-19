package backend.jvm.repository

import backend.jvm.model.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.sql.Time
import java.sql.Date

interface AppointmentRepository: JpaRepository<Appointment, Int>{

    fun getByScheduleId(id: Int): List<Appointment>

   // fun getAppoitmentByUser


    @Query(value = "select * from appointment where schedule_id = (select id from schedule s where s.company_id = :id)", nativeQuery = true)
    fun getAllOnGoingAppointments (@Param("id")id:Int): List<Appointment>


    @Query(value = "select * from appointment a where a.schedule_id=:sid and a.app_date=:date and a.app_hour=:hour", nativeQuery = true)
    fun getAppointmentByDateAndHour (@Param("sid")id:Int, @Param("date")date:Date, @Param("hour")hour:Time): List<Appointment>

    @Query(value = "select service_id from service_appointment where appointment_id=:id", nativeQuery = true)
    fun getServices(@Param("id") id:Int):List<Int>

    @Query(value = "select count(service_id) from appointment a where a.service_id = :serviceId and app_hour = :appHour and app_date = :appDate", nativeQuery = true)
    fun getNumberOfServicesByDateAndHour(@Param("serviceId") serviceId: Int, @Param("appDate") appDate: Date, @Param("appHour") appHour: Time): Int

    @Query(value =  "update appointment set number_app_people = :numberAppPeople where id = :id", nativeQuery = true)
    fun editNumberAppPeople(@Param("id") id: Int, @Param("numberAppPeople") numberAppPeople: Int): Appointment

    @Query(value = "UPDATE appointment SET number_app_people = number_app_people + 1, availability = CASE WHEN number_app_people + 1 = :maxNumber THEN 'unavailable' ELSE availability END WHERE id = :id", nativeQuery = true)
    fun increaseAppointmentNumber(@Param("id") id: Int, @Param("maxNumber") maxNumber: Int)

    @Query(value = "from appointment a where a.availability='available' and a.date=:date", nativeQuery = true)
    fun getAvailableAppointmentByDate(@Param("date") date: Date): Appointment

}
