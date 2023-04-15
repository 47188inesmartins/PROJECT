package backend.jvm.services


import backend.jvm.model.ServiceDB
import backend.jvm.repository.ServiceRepository
import backend.jvm.services.dto.ServiceInputDto
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

    fun addService(service: ServiceInputDto): ServiceDB {
        val company = companyServices.getCompany(service.companyId).get()
        println("company = $company")
        val serv = ServiceDB(
            name = service.serviceName,
            duration = Time.valueOf(service.duration),
            numberMax = service.numberMax,
            price = service.price,
            companyId= company
        )
        println("company = $company, ${serv.name}, ${serv.duration}, ${serv.numberMax}, ${serv.price}, ${serv.companyId.address}, ")
        return serviceRepository.save(serv)
    }

    fun getServiceById(id:Int): backend.jvm.model.ServiceDB {
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

    fun getEmployeeForService(idService:Int): backend.jvm.model.ServiceDB {
        return serviceRepository.getEmployeeForService(idService)
    }

    fun updatePrice(idService: Int,price: Double):Long{
        return serviceRepository.updatePrice(idService,price)
    }

    fun updateDuration(idService: Int,duration: String): Duration {
        return serviceRepository.updateDuration(idService,Time.valueOf(duration))
    }

    fun updateAvailability(idService: Int,idApp: Int,availability: String): String{
        return serviceRepository.updateAvailability(idService,idApp,availability)
    }

    fun updateMaxNumber(idService: Int,number: Int):Double{
        return serviceRepository.updateMaxNumber(idService,number)
    }

    fun delete(serviceDB: backend.jvm.model.ServiceDB){
        serviceRepository.delete(serviceDB)
    }

}