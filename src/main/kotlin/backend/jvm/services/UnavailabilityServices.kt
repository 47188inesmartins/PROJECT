package backend.jvm.services

import backend.jvm.repository.UnavailabilityRepository
import backend.jvm.repository.UserRepository
import backend.jvm.services.dto.UnavailabilityInputDto
import backend.jvm.services.dto.UnavailabilityOutputDto
import backend.jvm.services.interfaces.IUnavailabilityServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class UnavailabilityServices : IUnavailabilityServices {

    @Autowired
    lateinit var unavailabilityRepository: UnavailabilityRepository

    @Autowired
    lateinit var userRepository: UserRepository

    override fun addUnavailability(unavailability: UnavailabilityInputDto): UnavailabilityOutputDto {
        val getUser = userRepository.findById(unavailability.user).get()

        if(unavailability.dateEnd == null){
            if(unavailability.hourEnd == null || unavailability.hourBegin == null) throw Exception("hour end and hour begin can't be null")
        }

        val a = unavailability.mapToUnavailable(unavailability,getUser)
        val addUnavailability = unavailabilityRepository.save(
            a
        )
        println(addUnavailability)
        return UnavailabilityOutputDto(addUnavailability)
    }

    override fun getUnavailabilityByUser(user: Int): UnavailabilityOutputDto{
        val getUser = userRepository.findById(user).get()
        return UnavailabilityOutputDto(
            unavailabilityRepository.getUnavailabilityDBByUserDBId(getUser)
        )
    }

    override fun deleteUnavailability(id: Int){
        unavailabilityRepository.deleteById(id)
    }
}