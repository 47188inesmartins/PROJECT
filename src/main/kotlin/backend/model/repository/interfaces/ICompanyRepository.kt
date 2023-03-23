package backend.model.repository.interfaces

import backend.domain.Company
import backend.domain.DB.CompanyDB

interface ICompanyRepository {
    fun select(id: Int): Company
    fun delete(id: Int) : Boolean
    fun insert(company : Company) : Int?
}