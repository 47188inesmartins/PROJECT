package backend.jvm.repository

import backend.jvm.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param


interface RoleRepository : JpaRepository<Role, Int>{

    fun getRoleByName(name:String):Role

    @Query(value = "select role_name from user_role r where r.user_id = :id", nativeQuery = true)
    fun getRoleByUserId(@Param("id") id: Int):String

}