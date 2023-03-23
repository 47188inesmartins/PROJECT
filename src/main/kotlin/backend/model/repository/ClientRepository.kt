package backend.model.repository

import backend.domain.User
import backend.model.mappers.ClientMapper
import backend.model.repository.interfaces.IClientRepository

class ClientRepository:IClientRepository {

    private lateinit var userMapper: ClientMapper

    override fun insertUser(user: User): Int? = userMapper.add(user)

    override fun getUser(id: Int): User = userMapper.get(id)

    override fun deleteUser(id: Int) = userMapper.remove(id)

    override fun getUserByEmail(email: String): User = userMapper.getUserByEmail(email)

}