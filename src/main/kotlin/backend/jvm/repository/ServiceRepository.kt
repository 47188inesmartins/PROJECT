package backend.jvm.repository

import backend.jvm.model.ServiceDB
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.sql.Time
import java.time.Duration


interface ServiceRepository : JpaRepository<ServiceDB, Int> {


    @Query(value = "select count(appointment_id) from service_appointment where service_id=:serviceId and appointment_id=:appointmentId ", nativeQuery = true)
    fun countAppointments(@Param("serviceId") serviceId: Int, @Param("appointmentId") appointmentId: Int): Int

   // fun findAllByEmployee(employee: List<Employee>):Services
    @Query(value ="select * from service s " +
            "inner join employee_service e on e.service_id = :idService " +
            "inner join employee p on p.id = e.employee_id", nativeQuery = true)
    fun getEmployeeForService(@Param("idService") idService:Int):ServiceDB

    @Query(value ="update service set price =:price where id=:idService returning price", nativeQuery = true)
    fun updatePrice( @Param("idService") idService: Int, @Param("price") price: Double): Long

    @Query(value ="update service set duration =:duration where id=:idService returning duration  ", nativeQuery = true)
    fun updateDuration( @Param("idService") idService: Int, @Param("duration") duration: Time): Duration

    @Query(value ="update service set number_max =:number where id=:idService returning number_max  ", nativeQuery = true)
    fun updateMaxNumber( @Param("idService") idService: Int, @Param("number") number: Int):Double

    @Query(value = "update SERVICE_APPOINTMENT set availability =:availability where service_id =:idService and appointment_id=:idApp returning availability", nativeQuery = true)
    fun updateAvailability( @Param("idService") idService: Int,@Param("idApp") idApp: Int, @Param("availability") availability: String): String

/*/* @Query(value ="update service set number_max =:number where id=:idService  ", nativeQuery = true)
fun getServicesByIdAndNumberMax(id: Int, numberMax: Int): Services*/*/

    fun getServicesById(id: Int):ServiceDB
}