package backend.jvm.model.user

import backend.jvm.model.service.ServiceEntity
import backend.jvm.utils.AddressUtils
import backend.jvm.utils.Geolocation
import backend.jvm.utils.UserRoles
import java.sql.Date
import java.util.*


data class UserEmails(val emails: List<String>)

data class URoles(val token: Int, val role: String)

data class UserCredentials(val email: String, val password: String)

data class CreatedUserOutput(val id: Int, val token: UUID)

data class UserInfo(val id: Int, val name: String)

data class UserOutputDto(
        val id: Int,
        val token: UUID,
        val email: String,
        val password: String,
        val name: String,
        val birthday: String,
        val address: String,
        val companyId: List<Int?>?,
        val services: List<Int>,
        val profilePic: ByteArray?
){
        constructor(userEntity: UserEntity): this(
                id = userEntity.id,
                token = userEntity.token,
                email = userEntity.email,
                password = userEntity.password,
                name = userEntity.name,
                address = userEntity.address,
                birthday = userEntity.birthday.toString(),
                companyId = userEntity.companies?.map { it.company?.id },
                services = userEntity.services?.map { it.id }?: emptyList(),
                profilePic = userEntity.profilePic
        )

        private fun mapToUserOutput(list: List<ServiceEntity>?):List<Int>{
                if(list == null) return emptyList()
                return list.map { it.id }
        }
}

data class UserInputDto(
        val email: String,
        val password: String,
        val name: String,
        val birthday: String,
        val street: String,
        val city: String,
        val country: String,
        val interests: String
){
        fun mapToUser(dto: UserInputDto,
                      pass: String,
                   //   roles: List<Role>,
                      interests: String,
                      profilePic: ByteArray?,
                      coordinates: Geolocation
        ): UserEntity {
              return UserEntity(
                      coordinates,
                      email = dto.email,
                      password = pass,
                      clientName = dto.name,
                      birth = Date.valueOf(dto.birthday),
                      serv = null,
                      address = AddressUtils.addressInfo(street,city,country),
                      companies = null,
                      appointmentEntities = null,
                   //   roles = roles,
                      unavailabilityEntity = null,
                      interests = interests,
                      profilePic = profilePic,
                      status = "PENDING"
                )
        }
}


data class CompanyRole(val companyId: Int? = null, val role: String = UserRoles.GUEST.name)

data class ReceiveMoney(val money: String)

data class AppointmentEmployee( val appHour: String, val appDate: String, val email: String, val service: Int)