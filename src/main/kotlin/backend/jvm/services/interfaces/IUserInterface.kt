package backend.jvm.services.interfaces

import backend.jvm.model.UserCompany
import backend.jvm.model.UserDB
import backend.jvm.services.dto.*

interface IUserInterface {
    fun addUser(user: UserInputDto): CreatedUserOutput

    fun deleteUser(id: Int): Boolean

    fun getUserById(id: Int): UserOutputDto

   // fun getRoleByUser(id: Int):String?

    fun changeRole(id: Int, name: String): String

    fun getRoleByUserAndCompany (compId: Int, userId: Int): UserCompany

    fun changePassword(password: String, id: Int): String

    fun getUsersByEmailAndPassword (email: String, password: String): UserOutputDto

    fun getUserByToken(token: String): UserDB?

    fun addEmployee(id: Int, user: String): CreatedUserOutput

    fun getAllAppointmentsByUser(id: Int): AppointmentsUserInfo

}