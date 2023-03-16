package backend.repository

import backend.domain.Company
import backend.domain.DB.CompanyDB
import java.util.*

interface CompanyRepository {
    fun get(email: String): CompanyDB?
    fun remove(email: String) : Boolean
    fun add(company : Company) : Int?
}