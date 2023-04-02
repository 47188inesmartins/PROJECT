package backend.jvm.repository

import backend.jvm.model.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface AppointmentRepository: JpaRepository<Appointment, Int>{

    fun findAllByCid (cid: Client) : List<Appointment>
    fun findAllByEid (eid: Employee) : List<Appointment>
    fun findAllBySid (sid: Schedule) : List<Appointment>

    @Query(value =  "select * from company c where c.id = (select comp_id from schedule where id = :id)", nativeQuery = true)
    fun getCompany(@Param("id") id: Int): Company?



}
