package backend.model.repository

import backend.domain.Company
import backend.model.mappers.CompanyMapper
import backend.model.repository.interfaces.ICompanyRepository
import org.springframework.stereotype.Component
import javax.persistence.EntityManager


@Component
class CompanyRepository : ICompanyRepository {

    private lateinit var entityManager: EntityManager
    private lateinit var companyMapper: CompanyMapper

    override fun select(id: Int): Company = companyMapper.get(id)

    override fun delete(id: Int): Boolean = companyMapper.remove(id)

    override fun insert(company: Company): Int? = companyMapper.add(company)

}