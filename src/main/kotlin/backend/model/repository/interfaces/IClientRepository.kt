package backend.model.repository.interfaces
import backend.domain.User

interface IClientRepository {
    fun insertUser(user:User):Int?
    fun getUser(id:Int):User
    fun deleteUser(id:Int):Boolean
    fun getUserByEmail(email:String):User
}