package backend.jvm.services.dto

import backend.jvm.model.*
import java.sql.Date
import java.util.*


data class UserOutputDto(
        val id: Int,
        val token: UUID,
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
                token = userDB.token,
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
        val appointment: List<Int>?
){
        companion object{
                val EMAIL_FORMAT = Regex("""^\w+@\w+\.\w+$""")
                val PASSWORD_FORMAT = Regex("^(?=.*\\d)(?=.*[!@#\$%^&*])(?=.*[a-zA-Z]).{8,}$")
        }

        init {
                require(EMAIL_FORMAT.matches(email)){ "Invalid email"}
                require(PASSWORD_FORMAT.matches(password)){ "Insecure password"}
        }
      /*  email:String,
        password:String,
        clientName:String,
        birth:Date,
        serv: List<ServiceDB>?,
        company: Company?,
        appointments: List<Appointment>?,
        roles: List<Role>*/
        fun mapToUser(dto: UserInputDto,pass: String,services: List<ServiceDB>?,appointment: List<Appointment>?,comp: Company?, roles: List<Role>):UserDB{
              return UserDB(
                      email = dto.email,
                      password = pass,
                      clientName = dto.name,
                      birth = Date.valueOf(dto.birthday),
                      serv = services,
                      company = comp,
                      appointments = appointment,
                      roles = roles
                )
        }

}