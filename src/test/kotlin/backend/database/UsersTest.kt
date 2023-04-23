package backend.database

import backend.jvm.model.Company
import backend.jvm.model.Role
import backend.jvm.model.UserDB
import backend.jvm.repository.RoleRepository
import backend.jvm.repository.UserRepository
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit4.SpringRunner
import java.sql.Date
import java.util.*
import kotlin.test.assertEquals


@RunWith(SpringRunner::class)
@DataJpaTest
@AutoConfigureTestDatabase
class UsersTest {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var roleRepository: RoleRepository

    @Autowired
    private lateinit var entityManager: TestEntityManager


    @Test
    fun `get user`(){
        val userDB = UserDB(
            "test@123.com",
            "123456",
            "test",
            Date.valueOf("2000-01-01"),
            listOf(),
            listOf()
        )

        val role = Role("user",userDB)

        val userInsert = entityManager.persist(userDB)
        val roleInsert = entityManager.persist(role)
        entityManager.flush()

        println(userInsert.id)
        val get = userRepository.getRole(userInsert.id!!)

        assertEquals("user",get)
    }

}