package backend

import backend.jvm.model.Company
import backend.jvm.repository.CompanyRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class CompanyRepositoryTest {

    @Autowired
    lateinit var entityManager: TestEntityManager

    @Autowired
    lateinit var companyRepository: CompanyRepository

    @Test
    fun `whenFindById_thenReturnCompany`() {
        val company = Company(null, "111111111", "Rua do teste", "tester", "test", "testing", null, null, null)
        entityManager.persist(company)
        entityManager.flush()
        val companyFound = companyRepository.findByIdOrNull(company.id)
        assertThat(companyFound == company)
    }

    @Test
    fun `whenFindByNif_thenReturnCompany`() {
        val company = Company(null, "111111111", "Rua do teste", "tester", "test", "testing", null, null, null)
        entityManager.persist(company)
        entityManager.flush()
        val companyFound = companyRepository.findCompanyByNif(company.nif)
        assertThat(companyFound == company)
    }
}