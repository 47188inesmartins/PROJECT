package backend.database

import backend.jvm.model.Company
import backend.jvm.model.Service
import backend.jvm.repository.CompanyRepository
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


/*
@DataJpaTest
@RunWith(SpringRunner::class)
class CompanyRepositoryTest {

    @Autowired
    private lateinit var companyRepository: CompanyRepository

    @Test
    fun `Insert one company`() {
        val com = Company(
            nif = "250407671",
            address = "Rua Ferreira da Costa",
            compName = "Maf,Lda",
            compType = "Cabeleireiro",
            description = "Cabeleireira ",
            service = Service(),

        )

        val espera = companyRepository.save(com)
        println(espera.id)
        assertNotNull(espera.id)
    }

    @Test
    fun `select one company`() {

        val com = Company(
            nif = "250407671",
            address = "Rua Ferreira da Costa",
            compName = "Maf,Lda",
            compType = "Cabeleireiro",
            description = "Cabeleireira "
        )

        val company = companyRepository.save(com)

        val getCompany = companyRepository.findAllById(company.id!!)

        assertEquals(company,getCompany)
    }

    @Test
    fun `select one company by nif`() {

        val com = Company(
            nif = "250407671",
            address = "Rua Ferreira da Costa",
            compName = "Maf,Lda",
            compType = "Cabeleireiro",
            description = "Cabeleireira "
        )

        val company = companyRepository.save(com)

        val getCompany = companyRepository.findCompanyByNif("250407671")

        assertEquals(company,getCompany)
    }

}*/