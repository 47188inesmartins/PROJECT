package backend.jvm.dao

import backend.jvm.model.company.CompanyEntity
import backend.jvm.model.UserCompany
import backend.jvm.model.UserCompanyPK
import backend.jvm.model.user.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserCompanyDao : JpaRepository<UserCompany, UserCompanyPK> {

    fun findByCompanyAndUser(companyEntity: CompanyEntity, user: UserEntity): UserCompany?

    fun getUserCompanyByUserId(user_id: Int): List<UserCompany>

    @Query(value = "select u.role from user_company u where u.user_id=:user_id and u.company_id=:company_id", nativeQuery = true)
    fun getRoleByCompanyAndUser(@Param("company_id")company: Int, @Param("user_id") user_id: Int): String?

    @Query(value = "delete from user_company where user_id = :userId and company_id = :companyId and role = 'EMPLOYEE'", nativeQuery = true)
    fun deleteAllByCompanyAndUserAndRole(@Param("companyId") company: Int, @Param("userId") user_id: Int)

}