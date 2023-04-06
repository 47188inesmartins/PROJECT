package backend.jvm.repository

import backend.jvm.model.Manager
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ManagerRepository : JpaRepository<Manager, Int> {


    //edit appointment availability

    @Query(value = "update appointment a set availability='available' from inner join schedule s ON a.sid = :id inner join manager m ON m.comp_id = :id", nativeQuery = true)
    fun editAvailability(@Param("id") id:Int)


}