package backend.repository

import backend.domain.Company
import backend.domain.DB.CompanyDB
import org.springframework.stereotype.Component
import org.springframework.transaction.TransactionManager
import javax.persistence.EntityManager
@Component
class CompanyRepository() : ICompanyRepository{

    private lateinit var entityManager: EntityManager

    override fun get(email: String): CompanyDB? {
        TODO("Not yet implemented")
    }

    override fun remove(email: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun add(company: Company): Int? {
        entityManager.persist(company)
        return company.id
    }

   //fun addEmployee(email: String): Boolean
}