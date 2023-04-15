package backend.jvm.repository

import backend.jvm.model.UnavailabilityDB
import org.springframework.data.jpa.repository.JpaRepository

interface UnavailabilityRepository:JpaRepository<UnavailabilityDB,Int> {

    fun getUnavailabilityDBById(id: Int): UnavailabilityDB

}