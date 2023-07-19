package backend.database

import backend.jvm.dao.ServiceDao
import backend.jvm.model.company.CompanyEntity
import backend.jvm.model.service.ServiceEntity
import backend.jvm.utils.Geolocation
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull
import java.sql.Time
import org.assertj.core.api.Assertions


@DataJpaTest
class ServiceDaoRepositoryTest {

    @Autowired
    lateinit var entityManager: TestEntityManager

    @Autowired
    lateinit var serviceDaoRepository:ServiceDao

    @DataJpaTest
    class ServiceDaoTest {

        @Autowired
        lateinit var entityManager: TestEntityManager

        @Autowired
        lateinit var serviceRepository: ServiceDao

        @Test
        fun `saveService`() {
            val location = Geolocation(41.1579438,-8.629105299999999)
            val company = CompanyEntity("966123456",location,null, "111111111", "Rua do teste", "tester", "test", "testing", null, null, null)
            entityManager.persist(company)
            entityManager.flush()
            val service = ServiceEntity("nome", Time.valueOf("00:20:20"),20.0,company, null)
            entityManager.persist(service)
            entityManager.flush()
            val serviceFound = serviceRepository.findByIdOrNull(service.id)
            Assertions.assertThat(serviceFound?.id!! > 0)
        }

        @Test
        fun `getServicesByCompanyId`() {
            val location = Geolocation(41.1579438,-8.629105299999999)
            val company = CompanyEntity("966123456",location,null, "111111111", "Rua do teste", "tester", "test", "testing", null, null, null)
            entityManager.persist(company)
            entityManager.flush()
            val service = ServiceEntity("nome",Time.valueOf("00:20:20"),20.0,company, null)
            entityManager.persist(service)
            entityManager.flush()
            val services = serviceRepository.getAllServicesFromACompany(company.id)
            Assertions.assertThat(services.isNotEmpty())
        }
    }
}