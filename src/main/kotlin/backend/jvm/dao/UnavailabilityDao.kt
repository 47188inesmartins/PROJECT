package backend.jvm.dao

import backend.jvm.model.unavailability.UnavailabilityEntity
import backend.jvm.model.user.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UnavailabilityDao:JpaRepository<UnavailabilityEntity,Int> {

    fun getUnavailabilityEntitiesByUser(userEntityId: UserEntity): List<UnavailabilityEntity>?


}