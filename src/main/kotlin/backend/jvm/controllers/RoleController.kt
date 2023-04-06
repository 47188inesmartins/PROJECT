package backend.jvm.controllers

import backend.jvm.model.Role
import backend.jvm.services.RoleServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(("/role"))
class RoleController {

    @Autowired
    lateinit var roleServices: RoleServices

    @PostMapping
    fun addRole(@RequestBody role: Role): Role{
        return roleServices.addRole(role)
    }
}