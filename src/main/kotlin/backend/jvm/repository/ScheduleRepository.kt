package backend.jvm.repository

import backend.jvm.model.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ScheduleRepository: JpaRepository<Schedule, Int> {

        /**
         * @param idSchedule
         * @param idService
         * @return List<Service>
         */
        @Query(value = "select * from service inner join service_appointment p on p.service_id = :idService inner join appointment a on a.sid = :idSchedule ", nativeQuery = true)
        fun getAllServices(@Param("idSchedule") idSchedule: Int, @Param("idService") idService: Int): List<Services>


        /**
         * gets all clients scheduled for that hour and date
         * @param id
         * @param data
         * @param hour
         * @return List<Client>
         */
        @Query(value = "select * from client c inner join appointment a on c.id = a.cid and a.sid = :id and a.hour = :hour and a.app_date = :date ", nativeQuery = true)
        fun getAllClientSchedule(@Param("id") id:Int, @Param("date") date: String, @Param("hour") hour: String): List<Client>


        //fun findScheduleByAppointment(appointment: MutableList<Appointment>): Schedule

        //fun employeesAvaible() employee with company
}