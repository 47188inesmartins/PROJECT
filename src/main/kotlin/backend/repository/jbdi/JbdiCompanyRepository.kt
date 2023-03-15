package backend.repository.jbdi

import backend.repository.AppointmentRepository
import backend.repository.CompanyRepository
import org.jdbi.v3.core.Handle
import java.util.*
import org.jdbi.v3.core.kotlin.mapTo



class JbdiCompanyRepository(private val handle: Handle): CompanyRepository {
    override fun get(email: String): Boolean {
        TODO()

    }

    override fun remove(email: String, ) : Boolean {
       TODO()
    }

    override fun add(email: String, password: String, username: String, name: String, type: String, description: String) : Int? {
        return handle.createQuery("insert into dbo.company(email, password, username, comp_name, comp_type, description) " +
                "values (:email, :password, :username, :name, :type, :description) returning id"
        )
            .bind("email", email)
            .bind("password", password)
            .bind("username", username)
            .bind("name", name)
            .bind("type", type)
            .bind("description", description)
            .mapTo<Int>()
            .first()
    }
}