package backend.jvm.services

import backend.jvm.repository.UnavailabilityRepository
import backend.jvm.repository.UserRepository
import backend.jvm.services.dto.UnavailabilityInputDto
import backend.jvm.services.dto.UnavailabilityOutputDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class UnavailabilityServices {

    @Autowired
    lateinit var unavailabilityRepository: UnavailabilityRepository

    @Autowired
    lateinit var userRepository: UserRepository

    fun addUnavailability(unavailability: UnavailabilityInputDto): UnavailabilityOutputDto {
        val getUser = userRepository.findById(unavailability.user).get()
        println(getUser)

        val a = unavailability.mapToUnavailable(unavailability,getUser)
        println("maf bruxa"+ a)
        val addUnavailability = unavailabilityRepository.save(
            a
        )
        println(addUnavailability)
        return UnavailabilityOutputDto(addUnavailability)
    }

    fun getUnavailabilityByUser(user: Int): UnavailabilityOutputDto{
        val getUser = userRepository.findById(user).get()
        return UnavailabilityOutputDto(
            unavailabilityRepository.getUnavailabilityDBByUserId(getUser)
        )
    }

    fun deleteUnavailability(id: Int){
        unavailabilityRepository.deleteById(id)
    }
}