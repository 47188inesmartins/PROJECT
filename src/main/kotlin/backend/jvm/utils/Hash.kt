package backend.jvm.utils

import java.security.MessageDigest

class Hashing(){
    companion object{
        val passwordVerify = Regex("^(?=.*\\d)(?=.*[!@#\$%^&*])(?=.*[a-zA-Z]).{8,}$")
    }

    fun encodePass(pass:String): String? {
        if(!passwordVerify.matches(pass)) return null
        val bytes = pass.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }

}

