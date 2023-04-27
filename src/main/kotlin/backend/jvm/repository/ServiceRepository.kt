package backend.jvm.repository

import backend.jvm.model.ServiceDB
import backend.jvm.model.UserDB
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.sql.Date
import java.sql.Time
import java.time.Duration


interface ServiceRepository : JpaRepository<ServiceDB, Int> {


    @Query(name = "getServiceById")
    fun getServiceDBById(@Param("id") id: Int): ServiceDB?

    @Query(value = "select * from service s where s.company_id = :id", nativeQuery = true)
    fun getAllServicesFromACompany(@Param("id") id: Int): List<ServiceDB>


    @Query(value = "select count(appointment_id) from service_appointment where service_id=:serviceId and appointment_id=:appointmentId ", nativeQuery = true)
    fun countAppointments(@Param("serviceId") serviceId: Int, @Param("appointmentId") appointmentId: Int): Int

    @Query(value ="update service set price =:price where id=:idService returning price", nativeQuery = true)
    fun updatePrice( @Param("idService") idService: Int, @Param("price") price: Double): Long

    @Query(value ="update service set duration =:duration where id=:idService returning duration  ", nativeQuery = true)
    fun updateDuration( @Param("idService") idService: Int, @Param("duration") duration: Time): Duration

    @Query(value = "update service set number_max =:number where id=:idService returning number_max  ", nativeQuery = true)
    fun updateMaxNumber( @Param("idService") idService: Int, @Param("number") number: Int):Double


    @Query(value = "select * from service s where s.company_id = :companyId and s.id in" +
            " (select service_id from sch_day where week_days = :day and :beginHour between begin_hour and end_hour " +
            "and :endHour between begin_hour and end_hour)", nativeQuery = true)
    fun getAvailableServicesByDay( @Param("companyId") companyId: Int, @Param("day") day: String, @Param("beginHour") beginHour: Time, @Param("endHour") endHour: Time):Double

}

//9:00:00 11:00:00 -- 12:00:00 13:00:00 unavail

//11:30:00 - 13:30:00

// if in 11:30:00 - 13:30:00 houver um begin hour no unavailable retorna false