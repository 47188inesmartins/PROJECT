package backend.jvm.repository

import backend.jvm.model.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AppointmentRepository: JpaRepository<Appointment, Int>{
    fun findAllByCid (cid: Client) : List<Appointment>
    fun findAllByEid (eid: Employee) : List<Appointment>
    fun findAllBySid (sid: Schedule) : List<Appointment>
    @Query(value = ("select * from dbo.company where id = (select comp_id from dbo.schedule where id = ?1"))
    fun getCompany(id: Int): Company
}