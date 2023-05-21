package backend.jvm.services.dto

import backend.jvm.model.Company
import backend.jvm.model.Schedule
import backend.jvm.model.ServiceDB
import backend.jvm.model.UserDB

data class ServiceInfo(val id: Int, val name: String)

data class CompanyInputDto(
    val nif: String,
    val address: String,
    val name: String,
    val type: String,
    val description: String,
    val service: List<Int>?,
    val schedule: Int?,
    val users: List<Int>?
){
    fun mapToCompanyDto(dto: CompanyInputDto, service: List<ServiceDB>?, schedule: Schedule?, users: List<UserDB>?): Company{
        return Company(
            dto.nif,
            dto.address,
            dto.name,
            dto.type,
            dto.description,
            service,
            schedule,
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
    val users: List<Int>?
){
    constructor(company: Company): this(
        id = company.id,
        nif = company.nif,
        address = company.address,
        name = company.name,
        type = company.type,
        description = company.description,
        service = company.serviceDBS?.map { ServiceInfo(it.id, it.name) },
        schedule = company.schedule?.id,
        users = company.userDBS?.map { it.id }
    )
}