package backend.repository.jbdi

import backend.domain.Company
import backend.domain.DB.CompanyDB
import backend.repository.CompanyRepository
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.mapTo



class JbdiCompanyRepository(private val handle: Handle): CompanyRepository {
    override fun get(email: String): CompanyDB? {
        return handle.createQuery("select * from dbo.company where email = :email")
            .bind("email", email)
            .mapTo<CompanyDB>()
            .singleOrNull()
    }


    override fun remove(email: String, ) : Boolean {
       return handle.createQuery("select * from dbo.company where email = :email")
           .bind("email", email)
           .mapTo<Boolean>()
           .one()
    }

    override fun add(company : Company) : Int? {
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
    }

    override fun addEmployee(email: String): Boolean {
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
    }


}