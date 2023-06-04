package backend.jvm.services.dto

import backend.jvm.model.*
import java.sql.Date
import java.util.*


data class UserEmails(val emails: List<String>)

data class URoles(val companyId: Int, val role: String)

data class UserCredentials(val email: String, val password: String)

data class CreatedUserOutput(val id: Int, val token: UUID)


data class UserOutputDto(
        val id: Int,
        val token: UUID,
        val email: String,
        val password: String,
        val name: String,
        val birthday: String,
        val availability: String?,
        val companyId: List<Int?>?,
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
                companyId = userDB.companies?.map { it.company?.id },
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
        val unavailability: List<Int>?
){
        fun mapToUser(dto: UserInputDto,pass: String,services: List<ServiceDB>?,appointment: List<Appointment>?,comp: List<UserCompany>?, roles: List<Role>, unavailabilityDB : List<UnavailabilityDB>?):UserDB{
              return UserDB(
                      email = dto.email,
                      password = pass,
                      clientName = dto.name,
                      birth = Date.valueOf(dto.birthday),
                      serv = services,
                      companies = comp,
                      appointments = appointment,
                      roles = roles,
                      unavailabilityDB = unavailabilityDB
                )
        }
}


data class CompanyRole(val companyId: Int, val role: String)