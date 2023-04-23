package backend.jvm.services.dto

import backend.jvm.model.Appointment
import backend.jvm.model.Company
import backend.jvm.model.ServiceDB
import backend.jvm.model.UserDB
import java.sql.Date


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
        constructor(userDB: UserDB): this(
                id = userDB.id,
                email = userDB.email,
                password = userDB.password,
                name = userDB.name,
                birthday = userDB.birthday.toString(),
                availability = userDB.availability,
                companyId = userDB.company?.id,
                services = userDB.services?.map { it.id }?: emptyList()
        )

        private fun mapToUserOutput(list: List<ServiceDB>?):List<Int>{
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
        val services: List<Int>?,
        val appointment: List<Int>?,
){
        companion object{
                val EMAIL_FORMAT = Regex("""^\w+@\w+\.\w+$""")
                val PASSWORD_FORMAT = Regex("^(?=.*\\d)(?=.*[!@#\$%^&*])(?=.*[a-zA-Z]).{8,}$")
        }

        init {
                require(EMAIL_FORMAT.matches(email)){ "Invalid email"}
                require(PASSWORD_FORMAT.matches(password)){ "Insecure password"}
        }

        fun mapToUser(dto: UserInputDto,pass: String,services: List<ServiceDB>?,appointment: List<Appointment>?,comp: Company?):UserDB{
              return UserDB(
                      dto.email,
                      pass,
                      dto.name,
                      Date.valueOf(dto.birthday),
                      services,
                      comp,
                      appointment
                )
        }

}