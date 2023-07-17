package backend.database

import backend.jvm.dao.CompanyDao
import backend.jvm.dao.UserDao
import backend.jvm.model.UserCompany
import backend.jvm.model.appointment.AppointmentEntity
import backend.jvm.model.company.CompanyEntity
import backend.jvm.model.service.ServiceEntity
import backend.jvm.model.unavailability.UnavailabilityEntity
import backend.jvm.model.user.UserEntity
import backend.jvm.utils.Geolocation
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull
import java.sql.Date
import kotlin.test.assertEquals


@DataJpaTest
class UserRepositoryTest {

    @Autowired
    lateinit var entityManager: TestEntityManager

    @Autowired
    lateinit var userRepository: UserDao

    @Autowired
    lateinit var companyRepository: CompanyDao
    @Test
    fun `whenFindById_thenReturnUser`() {
        val location = Geolocation(41.1579438,-8.629105299999999)
        val user = UserEntity(location, "TEST@EMAIL.COM","pass", "teste", Date.valueOf("2001-01-10"), null, null, null,null,"BEAUTY","Rua alfredo",null,"VERIFIED")
        entityManager.persist(user)
        entityManager.flush()
        val companyFound =userRepository.findByIdOrNull(user.id)
        Assertions.assertThat(companyFound?.id!! > 0)
    }

    @Test
    fun `whenFindBytoken`() {
        val location = Geolocation(41.1579438,-8.629105299999999)
        val user = UserEntity(location, "TEST@EMAIL.COM","pass", "teste", Date.valueOf("2001-01-10"), null, null, null,null,"BEAUTY","Rua alfredo",null,"VERIFIED")
        entityManager.persist(user)
        entityManager.flush()
        val userFound =userRepository.getUserByToken(user.token)
        assertEquals(user,userFound)
    }

    @Test
    fun `whenFindByRole`() {
        val location = Geolocation(41.1579438,-8.629105299999999)
        val user = UserEntity(location, "TEST@EMAIL.COM","pass", "teste", Date.valueOf("2001-01-10"), null, null, null,null,"BEAUTY","Rua alfredo",null,"VERIFIED")
        entityManager.persist(user)
        entityManager.flush()
        val userFound =userRepository.getUserRoleByCompany(user.id,1)
        assertEquals(null,userFound)
    }

    @Test
    fun `updateByStatus`() {
        val location = Geolocation(41.1579438,-8.629105299999999)
        val user = UserEntity(location, "TEST@EMAIL.COM","pass", "teste", Date.valueOf("2001-01-10"), null, null, null,null,"BEAUTY","Rua alfredo",null,"VERIFIED")
        entityManager.persist(user)
        entityManager.flush()
        userRepository.changeStatusByEmail(user.email)
        val userFound =userRepository.getUserById(user.id)
        assertEquals("VERIFIED",userFound?.status)
    }
}