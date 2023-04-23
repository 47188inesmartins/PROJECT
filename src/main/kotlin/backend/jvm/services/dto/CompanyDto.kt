package backend.jvm.services.dto

import backend.jvm.model.Company

data class CompanyAddress(val address: String)
data class CompanyInputDto(
    val nif: String,
    val address: String,
    val name: String,
    val type: String,
    val description: String,
    val service: List<Int>?,
    val schedule: Int?,
    val users: List<Int>?
)

data class CompanyOutputDto(
    val id: Int,
    val nif: String,
    val address: String,
    val name: String,
    val type: String,
    val description: String,
    val service: List<Int>?,
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
        service = company.serviceDBS?.map { it.id },
        schedule = company.schedule?.id,
        users = company.userDBS?.map { it.id }
    )
}