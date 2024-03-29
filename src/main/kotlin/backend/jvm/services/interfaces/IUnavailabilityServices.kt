package backend.jvm.services.interfaces

import backend.jvm.services.dto.UnavailabilityInputDto
import backend.jvm.services.dto.UnavailabilityOutputDto

interface IUnavailabilityServices {
    fun addUnavailability(token: String?,unavailability: UnavailabilityInputDto): UnavailabilityOutputDto

    fun getUnavailabilityByUser(user: Int): List<UnavailabilityOutputDto>

    fun deleteUnavailability(id: Int)
}