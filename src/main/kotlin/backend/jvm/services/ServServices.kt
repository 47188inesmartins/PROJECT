package backend.jvm.services

import backend.jvm.model.Employee
import backend.jvm.model.Services
import backend.jvm.repository.ScheduleRepository
import backend.jvm.repository.ServiceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Time

@Service
class ServServices {
    @Autowired
    lateinit var serviceRepository: ServiceRepository

    fun getServiceById(id:Int): Services {
        return serviceRepository.findById(id).get()
    }

    /*fun findByEmployee(employee: List<Employee>):Services{
        return serviceRepository.findAllByEmployee(employee)
    }*/

    fun getEmployeeForService(idService:Int):Services{
        return serviceRepository.getEmployeeForService(idService)
    }

    fun updatePrice(idService: Int,price: Double):Services{
        return serviceRepository.updatePrice(idService,price)
    }

    fun updateDuration(idService: Int,duration: Time):Services{
        return serviceRepository.updateDuration(idService,duration)
    }

    fun updateMaxNumber(idService: Int,number: Int):Services{
        return serviceRepository.updateMaxNumber(idService,number)
    }

    fun delete(services: Services){
        serviceRepository.delete(services)
    }

}