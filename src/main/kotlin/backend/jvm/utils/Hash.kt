package backend.jvm.utils

import java.security.MessageDigest

class Hashing(){
    fun encodePass(pass:String): String {
        val bytes = pass.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }

}

