package backend.jvm.utils

import backend.jvm.services.UserServices
import backend.jvm.services.dto.UserInputDto
import java.security.MessageDigest

object Hashing{

    fun encodePass(pass:String): String{
        val bytes = pass.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }

    fun verifyPasswordSecure(pass: String) = UserServices.PASSWORD_FORMAT.matches(pass)


}

