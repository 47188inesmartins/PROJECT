package backend.jvm.model.service

import backend.jvm.model.company.CompanyEntity
import backend.jvm.model.day.DayEntity
import backend.jvm.model.user.UserEntity
import java.sql.Time

data class ServiceInputList(val services: List<Pair<ServiceInputDto, List<DayEntity>>>)

data class ServiceInputDto(
    val serviceName: String,
    val duration: String,
    val price: Double,
    val users: List<Int>?
){
    fun mapToService(db: ServiceInputDto, companyEntity: CompanyEntity, users: List<UserEntity>?) : ServiceEntity {
        val d = Time.valueOf(duration) ?: throw Exception("invalid time")
        return ServiceEntity(
            db.serviceName,
            d,
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
    val price: Double,
    val company: Int
){
    constructor(service : ServiceEntity): this(
        id = service.id,
        serviceName = service.name,
        duration = service.duration,
        price = service.price,
        company = service.company.id
    )
}