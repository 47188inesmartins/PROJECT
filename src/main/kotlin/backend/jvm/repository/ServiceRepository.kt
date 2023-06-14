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

    @Query(value = "select * from service s  where s.company_id = :id ", nativeQuery = true)
    fun getAllServicesFromACompany(@Param("id") id: Int): List<ServiceDB>

    @Query(value = "select count(appointment_id) from service_appointment where service_id=:serviceId and appointment_id=:appointmentId ", nativeQuery = true)
    fun countAppointments(@Param("serviceId") serviceId: Int, @Param("appointmentId") appointmentId: Int): Int

    @Query(value ="update service set price =:price where id=:idService returning price", nativeQuery = true)
    fun updatePrice( @Param("idService") idService: Int, @Param("price") price: Double): Long

    @Query(value ="update service set duration =:duration where id=:idService returning duration  ", nativeQuery = true)
    fun updateDuration( @Param("idService") idService: Int, @Param("duration") duration: Time): Duration

    @Query(value = "update service set number_max =:number where id=:idService returning number_max  ", nativeQuery = true)
    fun updateMaxNumber( @Param("idService") idService: Int, @Param("number") number: Int):Double

    @Query(value = "select s.id, s.service_name, s.duration, s.number_max, s.price, d.end_hour" +
            " s.company_id from service s inner join sch_day d on ( s.company_id = :companyId and" +
            " s.id = d.service_id and d.week_days = :day);", nativeQuery = true)
    fun getAvailableServicesByDay( @Param("companyId") companyId: Int,
                                   @Param("day") day: String
    ):List<Pair<ServiceDB, Time>>


    @Query(value = "select s.id,s.service_name,s.duration,s.number_max,s.price,s.company_id " +
            "from service s inner join SERVICE_DAY d on s.id = d.service_id inner join sch_day sd on sd.id = d.day_id and sd.week_days = :weekDay " +
            "and (:beginHour between sd.begin_hour and interval_begin or :beginHour between  interval_end and sd.end_hour) " +
            "and (TIME :beginHour + (s.duration || ' minutes')::INTERVAL between sd.begin_hour and interval_begin " +
            "or TIME :beginHour + (s.duration || ' minutes')::INTERVAL between  interval_end and sd.end_hour) " +
            "and s.company_id = :companyId ", nativeQuery = true)
    fun getAvailableServicesByHour(@Param("weekDay") weekDay: String, @Param("beginHour") beginHour: String, @Param("companyId") companyId: Int): List<ServiceDB>

    @Query(value = "select sum(s.price) as money from service s  " +
            "inner join USER_SERVICE US on s.id = US.service_id and US.user_id = :userId and s.company_id = :company " +
            "inner join APPOINTMENT_USER AU on AU.user_id = US.user_id " +
            "inner join APPOINTMENT A on A.id = AU.appointment_id " +
            "and A.service_id = s.id and a.app_date>:dateBegin and a.app_date<:dateEnd ", nativeQuery = true)
    fun getEarnedMoneyByEmployee(@Param("userId")userId: Int,@Param("company")company: Int, @Param("dateBegin") dateBegin: Date, @Param("dateEnd") dateEnd: Date): Double?

    @Query(value = "select * from service s where s.company_id = :company ", nativeQuery = true)
    fun getServiceDBByHourAndDate(@Param("company") company: Int):List<ServiceDB>
}
