package backend.jvm.repository

import backend.jvm.model.Company
import backend.jvm.model.UserCompany
import backend.jvm.model.UserCompanyPK
import backend.jvm.model.UserDB
import org.springframework.data.jpa.repository.JpaRepository

interface UserCompanyRepository : JpaRepository<UserCompany, UserCompanyPK> {

    fun getRoleByCompanyAndUser(company: Company, user: UserDB): UserCompany

    fun getAllByUser(user: UserDB): UserCompany?

    fun getByCompanyAndUser(company: Company, user: UserDB): UserCompany

    fun findByCompanyAndUser(company: Company, user: UserDB): UserCompany?

}