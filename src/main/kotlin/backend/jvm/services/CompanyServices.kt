package backend.jvm.services

import backend.jvm.model.Company
import backend.jvm.repository.CompanyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CompanyServices {

    @Autowired
    lateinit var companyRepository: CompanyRepository

    fun addCompany(company: Company): Company {
        return companyRepository.save(company)
    }

    fun deleteCompany(id: Int): Boolean{
        companyRepository.deleteById(id)
        return true
    }

    fun findAllById(id: Int): Company {
        return companyRepository.findAllById(id)
    }

    fun findCompanyByNif(nif: String): Company {
        return companyRepository.findCompanyByNif(nif)
    }

}