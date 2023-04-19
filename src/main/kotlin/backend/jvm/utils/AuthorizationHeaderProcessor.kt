package backend.jvm.utils

import backend.jvm.model.User
import backend.jvm.services.UserServices
import org.springframework.stereotype.Component

@Component
class AuthorizationHeaderProcessor(
    val userServices: UserServices
) {

    fun process(credentials : String?) : User?{
        if(credentials == null) return null

        val parts = credentials.trim().split(" ")

        if(parts.size != 2 || parts[0].lowercase() != SCHEMA) return null

        return userServices.getUserByToken(parts[1])
    }

    companion object{
        const val SCHEMA = "bearer"
    }
}