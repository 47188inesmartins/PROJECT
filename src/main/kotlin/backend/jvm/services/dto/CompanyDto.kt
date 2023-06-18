package backend.jvm.services.dto

import backend.jvm.model.*

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
    fun mapToCompanyDto(dto: CompanyInputDto, service: List<ServiceDB>?, users: List<UserCompany>?): Company{
        return Company(
            dto.nif,
            dto.address,
            dto.name,
            dto.type,
            dto.description,
            null,
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
    val path:String?
){
    constructor(company: Company): this(
        id = company.id,
        nif = company.nif,
        address = company.address,
        name = company.name,
        type = company.type,
        description = company.description,
        service = company.serviceDBS?.map { ServiceInfo(it.id, it.name,it.price,it.duration.toString()) },
        schedule = company.schedule?.id,
        users = company.userCompany?.map { CompanyUserRole(it.id.user, it.role)},
        path = company.url
    )
}

data class CompanyInfo(val id: Int, val name: String)
data class CompanyUserRole(val userId: Int, val role: String)