package backend.jvm.model.service

import backend.jvm.model.company.CompanyEntity
import backend.jvm.model.user.UserEntity
import java.sql.Time

data class ServiceInputList(val services: List<ServiceInputDto>)
data class ServiceInputDto(
    val serviceName: String,
    val duration: String,
    val numberMax: Int?,
    val price: Double,
    val users: List<Int>?

){
    fun mapToService(db: ServiceInputDto, companyEntity: CompanyEntity, users: List<UserEntity>?) : ServiceEntity {
        val d = Time.valueOf(duration) ?: throw Exception("invalid time")
        return ServiceEntity(
            db.serviceName,
            d,
            db.numberMax,
            db.price,
            companyEntity,
            users
        )
    }
}

data class ServicesOutputList(val servicesCreated: List<String>)
data class ServiceOutputDto(
    val id: Int,
    val serviceName: String,
    val duration: Time,
    val numberMax: Int?,
    val price: Double,
    val company: Int
){
    constructor(service : ServiceEntity): this(
        id = service.id,
        serviceName = service.name,
        duration = service.duration,
        numberMax = service.numberMax,
        price = service.price,
        company = service.company.id
    )
}