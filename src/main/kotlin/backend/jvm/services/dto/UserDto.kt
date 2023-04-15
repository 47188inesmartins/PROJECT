package backend.jvm.services.dto

import backend.jvm.model.ServiceDB
import backend.jvm.model.User


data class UserOutputDto(
        val id: Int,
        val email: String,
        val password: String,
        val name: String,
        val birthday: String,
        val availability: String?,
        val companyId: Int?,
        val services: List<Int>
){
        constructor(user: User): this(
                id = user.id,
                email = user.email,
                password = user.password,
                name = user.name,
                birthday = user.birthday.toString(),
                availability = user.availability,
                companyId = user.company?.id,
                services = user.services?.map { it.id }?: emptyList()
        )

        private fun mapToInt(list: List<ServiceDB>?):List<Int>{
                if(list == null) return emptyList()
                return list.map { it.id }
        }
}

data class UserInputDto(
        val email: String,
        val password: String,
        val name: String,
        val birthday: String,
        val availability: String?,
        val companyId: Int?,
        val services: List<Int>
){
        companion object{
                val EMAIL_FORMAT = Regex("""^\w+@\w+\.\w+$""")
                val PASSWORD_FORMAT = Regex("^(?=.*\\d)(?=.*[!@#\$%^&*])(?=.*[a-zA-Z]).{8,}$")
        }

        init {
                require(EMAIL_FORMAT.matches(email)){ "Invalid email"}
                require(PASSWORD_FORMAT.matches(password)){ "Insecure password"}
        }
}