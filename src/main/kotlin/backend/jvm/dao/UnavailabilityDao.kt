package backend.jvm.dao

import backend.jvm.model.unavailability.UnavailabilityEntity
import backend.jvm.model.user.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.sql.Date
import java.sql.Time

interface UnavailabilityDao:JpaRepository<UnavailabilityEntity,Int> {

    fun getUnavailabilityEntitiesByUser(userEntityId: UserEntity): List<UnavailabilityEntity>?

    fun deleteByDateBeginAndHourBeginAndUser(dateBegin: Date, hourBegin: Time, user: UserEntity)

}