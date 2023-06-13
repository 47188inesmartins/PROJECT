package backend.jvm.repository

import backend.jvm.model.*
import org.springframework.transaction.annotation.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.sql.Time


interface ScheduleRepository: JpaRepository<Schedule, Int> {


        @Query(name = "getScheduleById")
        fun getScheduleById(@Param("id") id: Int): Schedule?

        fun getScheduleByCompany_Id(@Param("id") id: Int): Schedule?

        @Modifying
        @Query(value = "update schedule set between_interval = :interval where id = :id ", nativeQuery = true)
        @Transactional
        fun updateBetweenInterval(@Param("id") id: Int,@Param("interval") interval: Time)


        /**
         * @param idSchedule
         * @param idService
         * @return List<Service>

        @Query(value = "from service c inner join service_appointment p on p.service_id = :idService inner join appointment a on a.sid = :idSchedule ", nativeQuery = true)
        fun getAllServices(@Param("idSchedule") idSchedule: Int, @Param("idService") idService: Int): List<Services>
        */

        /**
         * gets all clients scheduled for that hour and date
         * @param id
         * @param data
         * @param hour
         * @return List<Client>
         */
        /*@Query(value = "from client c inner join appointment a on c.id = a.cid and a.sid = :id and a.hour = :hour and a.app_date = :date ")
        fun getAllClientSchedule(@Param("id") id:Int, @Param("date") date: String, @Param("hour") hour: String): List<User>
*/

        //fun findScheduleByAppointment(appointment: MutableList<Appointment>): Schedule

        //fun employeesAvaible() employee with company
}