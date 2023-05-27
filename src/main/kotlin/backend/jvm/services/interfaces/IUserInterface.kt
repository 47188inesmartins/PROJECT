package backend.jvm.services.interfaces

import backend.jvm.model.UserDB
import backend.jvm.services.dto.*

interface IUserInterface {
    fun addUser(user: UserInputDto): CreatedUserOutput

    fun deleteUser(id: Int): Boolean

    fun getUserById(id: Int): UserOutputDto

    fun getRole(id: Int):String?

    fun changeAvailability(availability: String, id: Int): String

    fun changePassword(password: String, id: Int): String

    fun getUsersByEmailAndPass (email: String, password: String): UserOutputDto

    fun getUserByToken(token: String): UserDB?

    fun addEmployee(id: Int, user: String): CreatedUserOutput

    fun getAllAppointments(id: Int): AppointmentsUserInfo

    fun changeRole(id: Int, name: String): String
}