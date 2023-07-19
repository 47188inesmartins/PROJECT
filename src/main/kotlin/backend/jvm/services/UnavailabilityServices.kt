package backend.jvm.services

import backend.jvm.dao.UnavailabilityDao
import backend.jvm.dao.UserDao
import backend.jvm.services.dto.UnavailabilityInputDto
import backend.jvm.services.dto.UnavailabilityOutputDto
import backend.jvm.services.interfaces.IUnavailabilityServices
import backend.jvm.utils.errorHandling.UserNotFound
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


@Service
class UnavailabilityServices : IUnavailabilityServices {

    @Autowired
    lateinit var unavailabilityDao: UnavailabilityDao

    @Autowired
    lateinit var userDao: UserDao

    override fun addUnavailability(token: String?, unavailability: UnavailabilityInputDto): UnavailabilityOutputDto {
        if(token == null) throw UserNotFound()
        val getUser = userDao.getUserByToken(UUID.fromString(token))!!

        if(unavailability.dateEnd == null){
            if(unavailability.hourEnd == null || unavailability.hourBegin == null) throw Exception("hour end and hour begin can't be null")
        }

        val a = unavailability.mapToUnavailable(unavailability,getUser)
        val addUnavailability = unavailabilityDao.save(a)
        println(addUnavailability)
        return UnavailabilityOutputDto(addUnavailability)
    }

    override fun getUnavailabilityByUser(user: Int): List<UnavailabilityOutputDto>{
        val getUser = userDao.findById(user).get()
        val ret = unavailabilityDao.getUnavailabilityEntitiesByUser(getUser)
        return ret?.map{  UnavailabilityOutputDto(it) } ?: emptyList()

    }

    override fun deleteUnavailability(id: Int){
        unavailabilityDao.deleteById(id)
    }

    fun getEmployeesUnavailability(cid: Int):List<UnavailabilityOutputDto>{
        val employees = userDao.getUsersEmployeesByCompany(cid) ?: throw UserNotFound()
        val unUser = mutableListOf<UnavailabilityOutputDto>()
        employees.forEach {
            unavailabilityDao.getUnavailabilityEntitiesByUser(it)?.forEach { unavailability ->
                unUser.add(UnavailabilityOutputDto(unavailability))
            }
        }
        return unUser
    }
}