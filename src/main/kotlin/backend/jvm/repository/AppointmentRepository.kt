package backend.jvm.repository

import backend.jvm.model.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AppointmentRepository: JpaRepository<Appointment, Int>{
    fun findAllByCid (cid: Client) : List<Appointment>
    fun findAllByEid (eid: Employee) : List<Appointment>
    fun findAllBySid (sid: Schedule) : List<Appointment>

    /*
    @Query(value = "SELECT * FROM USERS u WHERE u.status = 1", nativeQuery = true)
    fun findAllActiveUsersNative(): Collection<User?>?

    @Query(value =  "select comp from company comp where comp.id = (select comp_id from schedule where id = :id)")
    fun getCompany(id: Int): Company?
*/
}
