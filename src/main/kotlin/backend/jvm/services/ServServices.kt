package backend.jvm.services


import backend.jvm.model.ServiceDB
import backend.jvm.model.User
import backend.jvm.repository.ServiceRepository
import backend.jvm.services.dto.ServiceInputDto
import backend.jvm.services.dto.ServiceOutputDto
import backend.jvm.services.dto.UserOutputDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Time
import java.time.Duration

@Service
class ServServices {

    @Autowired
    lateinit var serviceRepository: ServiceRepository
    @Autowired
    lateinit var companyServices : CompanyServices

    fun addService(service: ServiceInputDto): ServiceOutputDto {
        val company = companyServices.getCompany(service.company).get()
        val serv = ServiceDB(
            name = service.serviceName,
            duration = Time.valueOf(service.duration),
            numberMax = service.numberMax,
            price = service.price,
            company= company
        )
        val savedService = serviceRepository.save(serv)
        return ServiceOutputDto(savedService)
    }

    fun getServiceById(id: Int): ServiceOutputDto {
        val serv = serviceRepository.getServiceDBById(id)
        return ServiceOutputDto(serv)
    }

    fun getAvailableEmployees(id: Int): List<UserOutputDto>{
        val users = serviceRepository.getEmployeesForService(id)
        return users.map { UserOutputDto(it) }
    }

   /* fun verifyAvailability(idService: Int, idApp: Int): String {
        val countAppointment = serviceRepository.countAppointments(idService, idApp)
        val getService = getServiceById(idService)
        if(getService.numberMax == countAppointment){
            return serviceRepository.updateAvailability(idService, idApp, "unavailable")
        }
        return serviceRepository.updateAvailability(idService, idApp, "available")
    }*/
    /*
    fun findByEmployee(employee: List<Employee>):Services{
        return serviceRepository.findAllByEmployee(employee)
    }*/

    fun updatePrice(idService: Int,price: Double):Long{
        return serviceRepository.updatePrice(idService,price)
    }

    fun updateDuration(idService: Int,duration: String): Duration {
        return serviceRepository.updateDuration(idService,Time.valueOf(duration))
    }

 /*   fun updateAvailability(idService: Int,idApp: Int,availability: String): String{
        return serviceRepository.updateAvailability(idService,idApp,availability)
    }
*/
    fun updateMaxNumber(idService: Int,number: Int):Double{
        return serviceRepository.updateMaxNumber(idService,number)
    }

    fun delete(serviceDB: ServiceDB){
        serviceRepository.delete(serviceDB)
    }

}