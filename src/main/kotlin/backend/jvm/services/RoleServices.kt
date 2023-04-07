package backend.jvm.services

import backend.jvm.model.Role
import backend.jvm.repository.RoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RoleServices {


    @Autowired
    lateinit var roleRepository: RoleRepository

    fun addRole(role: Role): Role {
        return roleRepository.save(role)
    }

    fun deleteRole(id: Int): Boolean {
        roleRepository.deleteById(id)
        return true
    }

}