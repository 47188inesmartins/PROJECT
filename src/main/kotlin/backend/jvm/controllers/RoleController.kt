package backend.jvm.controllers

import backend.jvm.model.Role
import backend.jvm.services.RoleServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.server.ResponseStatusException


@RestController
@RequestMapping(("/api/role"))
class RoleController {

    @Autowired
    lateinit var roleServices: RoleServices

    @PostMapping
    fun addRole(@RequestBody role: Role): Role{
        return roleServices.addRole(role)
    }

    @GetMapping
    fun getRoleByToken(): ResponseEntity<String>{
        return try {
            val requestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
            val request = requestAttributes.request
            val bearerToken = request.getHeader("Authorization")?.removePrefix("Bearer ")
            val response = bearerToken?.let {  roleServices.getRoleByToken(it) }
            ResponseEntity
               .status(200)
               .body(response)
        }catch (e: Exception){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
        }
    }
}