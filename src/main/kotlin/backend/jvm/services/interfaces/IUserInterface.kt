package backend.jvm.services.interfaces

import backend.jvm.model.Appointment
import backend.jvm.model.UserCompany
import backend.jvm.model.UserDB
import backend.jvm.services.dto.*
import org.springframework.web.multipart.MultipartFile

interface IUserInterface {
    fun addUser(user: UserInputDto): CreatedUserOutput

    fun deleteUser(id: Int): Boolean

    fun getUserById(token: String): UserOutputDto

    fun getEarnedMoneyEmployee(userId: String, dateBegin: String, dateEnd: String,company: Int): Double

    fun changeRole(id: Int, name: String): String

    fun getRoleByUserAndCompany (compId: Int, userId: Int): String?

    fun changePassword(password: String, id: Int): String

    fun getUsersByEmailAndPassword (email: String, password: String): UserOutputDto

    fun getRoleByUserIdAndCompany(company: Int, user_id: String): String?

    fun getUserByToken(token: String): UserDB?

    fun addEmployees(companyId: Int, emails: List<String>)

    fun getAllAppointmentsByUser(token: String): AppointmentsUserInfo

    fun updateUserProfilePicture(id: Int, image: MultipartFile)

    fun getPersonalizedCompanies(token: String?): List<CompanyOutputDto>?

    fun mapToAppointmentsInfo(listAppointments: List<Appointment>):List<AppointmentInfo>

}