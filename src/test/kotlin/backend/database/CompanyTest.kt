package backend.database

import backend.jvm.model.Company
import backend.jvm.repository.CompanyRepository
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.assertEquals


@RunWith(SpringRunner::class)
@DataJpaTest
@AutoConfigureTestDatabase
class CompanyRepositoryTest {

    @Autowired
    private lateinit var companyRepository: CompanyRepository

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Test
    fun `Insert one company`() {
        val com = Company(
            nif = "250407671",
            address = "Rua Ferreira da Costa",
            compName = "Maf,Lda",
            compType = "Cabeleireiro",
            description = "Cabeleireira ",
            listOf(),
            null,
            listOf()
        )

        val a = entityManager.persist(com)
        entityManager.flush()

        val c = companyRepository.findAllById(a.id!!)

        assertEquals(a.id,c.id)
    }
}