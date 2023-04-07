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

    @Query(value =  "from company c where c.id = (select comp_id from schedule where id = :id)")
    fun getCompany(@Param("id") id: Int): Company?

    @Query(value =  "update appointment set number_app_people = :numberAppPeople where id = :id")
    fun editNumberAppPeople(@Param("id") id: Int, @Param("numberAppPeople") numberAppPeople: Int): Appointment

    @Query(value =  "update appointment set availability = :availability where id = :id")
    fun editAvailability(@Param("id") id: Int, @Param("availability") availability: String): Appointment

    @Query(value =  "update appointment set number_app_people = number_app_people + 1 where id = :id")
    fun appointmentNumber(@Param("id") id: Int)

    fun getAppointmentByIdAndAvailability(id: Int, availability: String): Appointment

}
