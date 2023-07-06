package backend.jvm.model.company

import backend.jvm.model.*
import backend.jvm.model.service.ServiceEntity

data class ServiceInfo(val id: Int, val name: String, val price: Double, val duration: String)

data class CompanyInputDto(
    val nif: String,
    val address: String,
    val name: String,
    val type: String,
    val description: String,
    val service: List<Int>?,
    val users: MutableList<Int>?
){
    fun mapToCompanyDto(dto: CompanyInputDto, service: List<ServiceEntity>?, users: List<UserCompany>?): CompanyEntity {
        return CompanyEntity(
            null,
            dto.nif,
            dto.address,
            dto.name,
            dto.type,
            dto.description,
            service,
            null,
            users
        )
    }
}

data class CompanyOutputDto(
    val id: Int,
    val nif: String,
    val address: String,
    val name: String,
    val type: String,
    val description: String,
    val service: List<ServiceInfo>?,
    val schedule: Int?,
    val users: List<CompanyUserRole>?,
    val path: List<ByteArray?>?
){
    constructor(companyEntity: CompanyEntity): this(
        id = companyEntity.id,
        nif = companyEntity.nif,
        address = companyEntity.address,
        name = companyEntity.name,
        type = companyEntity.type,
        description = companyEntity.description,
        service = companyEntity.service?.map { ServiceInfo(it.id, it.name,it.price,it.duration.toString()) },
        schedule = companyEntity.schedule?.id,
        users = companyEntity.userCompany?.map { CompanyUserRole(it.id.user, it.role) },
        path = companyEntity.entities?.map { it.image }
    )
}

data class CompanyInfo(val id: Int, val name: String)
data class CompanyUserRole(val userId: Int, val role: String)