package backend.jvm.services.interfaces

import backend.jvm.model.appointment.AppointmentEntity
import backend.jvm.model.appointment.AppointmentInfo
import backend.jvm.model.appointment.AppointmentsUserInfo
import backend.jvm.model.company.CompanyOutputDto
import backend.jvm.model.user.*
import org.springframework.web.multipart.MultipartFile

interface IUserInterface {
    fun addUser(user: UserInputDto): CreatedUserOutput

    fun deleteUser(id: Int): Boolean

    fun getUserById(token: String): UserOutputDto

    fun getEarnedMoneyEmployee(userId: String, dateBegin: String, dateEnd: String,company: Int): Double

    fun changeRole(id: Int, name: String): String

    fun getRoleByUserAndCompany (compId: Int, userId: Int): String?

    fun changePassword(password: String, id: Int): String

    //fun getUsersByEmailAndPassword (email: String, password: String): Pair<String, List<CompanyRole>>

    fun getRoleByUserIdAndCompany(company: Int, user_id: String): String?

    fun getUserByToken(token: String): UserEntity?

    fun addEmployees(companyId: Int, emails: List<String>)

    fun getAllAppointmentsByUser(token: String): AppointmentsUserInfo

    fun updateUserProfilePicture(id: Int, image: MultipartFile)

    fun mapToAppointmentsInfo(listAppointmentEntities: List<AppointmentEntity>):List<AppointmentInfo>

}