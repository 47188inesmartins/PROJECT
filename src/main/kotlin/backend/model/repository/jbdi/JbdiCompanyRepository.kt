package backend.model.repository.jbdi

import backend.domain.Company
import backend.domain.DB.CompanyDB
import backend.model.repository.interfaces.ICompanyRepository
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.mapTo



class JbdiCompanyRepository(private val handle: Handle): ICompanyRepository {


    override fun select(id: Int): Company {
        TODO("Not yet implemented")
    }

    override fun delete(id: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun insert(company: Company): Int? {
        TODO("Not yet implemented")
    }
    /*override fun addEmployee(email: String): Boolean {
        return handle.createQuery("insert into dbo.company(email, password, comp_name, comp_type, description) " +
                "values (:email, :password, :username, :type, :description) returning id"
        )
            .bind("email", company.email)
            .bind("password", company.password)
            .bind("username", company.compName)
            .bind("type", company.compType)
            .bind("description", company.description)
            .mapTo<Int>()
            .first()
    }*/


}