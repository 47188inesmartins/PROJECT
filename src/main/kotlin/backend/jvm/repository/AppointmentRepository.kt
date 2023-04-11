package backend.jvm.repository

import backend.jvm.model.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.sql.Time
import java.util.*

interface AppointmentRepository: JpaRepository<Appointment, Int>{

    /*
    fun findAllByCid (cid: Client) : List<Appointment>
    fun findAllByEid (eid: Employee) : List<Appointment>

    */
    fun findAllBySid (sid: Schedule) : List<Appointment>

    fun findAllByAppHourAndUidAndAppDate (appHour: Time, user: User, appDate: Date): Appointment

    fun findAllByAppHourAndSidAndAppDate (appHour: Time, sid: Schedule, appDate: Date) : Appointment

    @Query(value = "select * from appointment a where a.sid=:sid and a.app_date=:date and a.app_hour=:hour", nativeQuery = true)
    fun getAppointmentByDateAndHour (@Param("sid")id:Int, @Param("date")date:Date, @Param("hour")hour:Time): List<Appointment>

 /*
    fun findAllByAppDateAndAppHourAndSid(appDate: Date, appHour: Time, sid: Schedule): List<Appointment>?

 /*   @Query(value =  "from company c where c.id = (select comp_id from schedule where id = :id)")
    fun getCompany(@Param("id") id: Int): Company?
*/

    @Query(value = "select service_id from service_appointment where appointment_id=:id", nativeQuery = true)
    fun getServices(@Param("id") id:Int):List<Services>

    @Query(value =  "update appointment set number_app_people = :numberAppPeople where id = :id", nativeQuery = true)
    fun editNumberAppPeople(@Param("id") id: Int, @Param("numberAppPeople") numberAppPeople: Int): Appointment

   /* @Query(value =  "update appointment set availability = :availability where id = :id",
)
    fun editAvailability(@Param("id") id: Int, @Param("availability") availability: String): Appointment

    @Query(value = "UPDATE appointment SET number_app_people = number_app_people + 1, availability = CASE WHEN number_app_people + 1 = :maxNumber THEN 'unavailable' ELSE availability END WHERE id = :id", nativeQuery = true)
    fun increaseAppointmentNumber(@Param("id") id: Int, @Param("maxNumber") maxNumber: Int)

    @Query(value = "from appointment a where a.availability='available' and a.date=:date", nativeQuery = true)
    fun getAvailableAppointmentByDate(@Param("date") date: Date): Appointment

}
