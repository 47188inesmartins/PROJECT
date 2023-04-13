package backend.jvm.services


import backend.jvm.model.Services
import backend.jvm.repository.ServiceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Time
import java.util.*

@Service
class ServServices {

    @Autowired
    lateinit var serviceRepository: ServiceRepository

    fun addService(services: Services):Services{
        return serviceRepository.save(services)
    }


    fun getServiceById(id:Int): Services {
     return serviceRepository.getServicesById(id)
    }

    fun verifyAvailability(idService: Int, idApp: Int): String {
        val countAppointment = serviceRepository.countAppointments(idService, idApp)
        val getService = getServiceById(idService)
        if(getService.numberMax == countAppointment){
            return serviceRepository.updateAvailability(idService, idApp, "unavailable")
        }
        return serviceRepository.updateAvailability(idService, idApp, "available")
    }
    /*
    fun findByEmployee(employee: List<Employee>):Services{
        return serviceRepository.findAllByEmployee(employee)
    }*/

    fun getEmployeeForService(idService:Int):Services{
        return serviceRepository.getEmployeeForService(idService)
    }

    fun updatePrice(idService: Int,price: Double):Long{
        return serviceRepository.updatePrice(idService,price)
    }

    fun updateDuration(idService: Int,duration: String):Services{
        return serviceRepository.updateDuration(idService,Time.valueOf(duration))
    }

    fun updateAvailability(idService: Int,duration: String){
    TODO()
    //  serviceRepository.update
    }

    fun updateMaxNumber(idService: Int,number: Int):Services{
        return serviceRepository.updateMaxNumber(idService,number)
    }

    fun delete(services: Services){
        serviceRepository.delete(services)
    }

}