package backend.jvm.repository

import backend.jvm.model.Company
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CompanyRepository : JpaRepository<Company, Int>{

    fun findAllById(id: Int): Company
    fun findCompanyByNif(nif:String): Company

}
