package backend.jvm.repository

import backend.jvm.model.Role
import org.springframework.data.jpa.repository.JpaRepository


interface RoleRepository : JpaRepository<Role, Int>{

    fun getRoleById(id:Int):Role

}