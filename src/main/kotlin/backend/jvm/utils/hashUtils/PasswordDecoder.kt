package backend.jvm.utils.hashUtils

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordDecoder {

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    /**
     * Compares if the inserted password is the same that the one stored
     * @param storedPassword the password saved on the database
     * @param insertPassword the password inserted by the user
     * @return Boolean true if the passwords matches otherwise returns false
     */
    fun getSavedPassword(storedPassword: String, insertPassword: String):Boolean{
        return (passwordEncoder.matches(insertPassword,storedPassword))
    }
}

