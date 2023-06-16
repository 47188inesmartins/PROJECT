package backend.jvm.services.interfaces

import backend.jvm.model.UserCompany
import backend.jvm.model.UserDB
import backend.jvm.services.dto.*

interface IUserInterface {
    fun addUser(user: UserInputDto): CreatedUserOutput

    fun deleteUser(id: Int): Boolean

    fun getUserById(token: String): UserOutputDto

   // fun getRoleByUser(id: Int):String?

    fun changeRole(id: Int, name: String): String

    fun getRoleByUserAndCompany (compId: Int, userId: Int): String?

    fun changePassword(password: String, id: Int): String

    fun getUsersByEmailAndPassword (email: String, password: String): UserOutputDto

    fun getUserByToken(token: String): UserDB?

    fun addEmployees(companyId: Int, emails: List<String>)

    fun getAllAppointmentsByUser(token: String): AppointmentsUserInfo

}