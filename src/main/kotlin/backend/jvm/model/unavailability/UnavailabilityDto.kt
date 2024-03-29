package backend.jvm.services.dto

import backend.jvm.model.unavailability.UnavailabilityEntity
import backend.jvm.model.user.UserEntity
import backend.jvm.utils.getCurrentDate
import java.sql.Date
import java.sql.Time

data class UnavailabilityInputDto(
    val dateBegin: String?,
    val dateEnd: String?,
    val hourBegin: String?,
    val hourEnd: String?
){
    fun mapToUnavailable(dto: UnavailabilityInputDto, userEntity: UserEntity) : UnavailabilityEntity {
        val db = if(dto.dateBegin == null){  getCurrentDate() }else {
            Date.valueOf(dto.dateBegin)}
        println(db)
        val de = if(dto.dateEnd != null )Date.valueOf(dto.dateEnd) else null
        println(de)
        val hb = if(dto.hourBegin != null )Time.valueOf(dto.hourBegin.plus(":00")) else null
        println(hb)
        val he = if(dto.hourEnd != null )Time.valueOf(dto.hourEnd.plus(":00")) else null
        println(he)
        println(dto)
        return UnavailabilityEntity(
            dateBegin = db,
            dateEnd = de,
            hourBegin = hb,
            hourEnd =  he,
            userEntity = userEntity
        )
    }
}

data class UnavailabilityOutputDto(
    val id: Int,
    val dateBegin: String,
    val dateEnd: String,
    val hourBegin: String,
    val hourEnd: String,
    val user: String
){
    constructor(unavailability: UnavailabilityEntity): this(
        id = unavailability.id,
        dateBegin = unavailability.dateBegin.toString(),
        dateEnd = unavailability.dateEnd.toString(),
        hourBegin = unavailability.hourBegin.toString(),
        hourEnd = unavailability.hourEnd.toString(),
        user = unavailability.user.email
    )
}

