package backend.jvm.repository

import backend.jvm.model.Company
import backend.jvm.model.UserCompany
import backend.jvm.model.UserCompanyPK
import backend.jvm.model.UserDB
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserCompanyRepository : JpaRepository<UserCompany, UserCompanyPK> {

    fun getRoleByCompanyAndUser(company: Company, user: UserDB): UserCompany

    fun getAllByUser(user: UserDB): UserCompany?

    fun getByCompanyAndUser(company: Company, user: UserDB): UserCompany

    fun findByCompanyAndUser(company: Company, user: UserDB): UserCompany?

    fun getUserCompanyByUserId(user_id: Int): List<UserCompany>

    @Query(value = "select u.role from user_company u where u.user_id=:user and u.company_id=:company", nativeQuery = true)
    fun getRoleByCompanyAndUser(@Param("company")company: Int, @Param("user_id") user_id: Int): String
}