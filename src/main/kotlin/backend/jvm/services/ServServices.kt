package backend.jvm.services


import backend.jvm.model.ServiceDB
import backend.jvm.repository.ServiceRepository
import backend.jvm.repository.UserRepository
import backend.jvm.services.dto.ServiceInputDto
import backend.jvm.services.dto.ServiceOutputDto
import backend.jvm.services.dto.UserOutputDto
import backend.jvm.utils.errorHandling.CompanyNotFound
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Service
import java.sql.Date
import java.sql.Time
import java.time.Duration

@Service
class ServServices {

    @Autowired
    lateinit var serviceRepository: ServiceRepository
    @Autowired
    lateinit var companyServices : CompanyServices
    @Autowired
    lateinit var userRepository : UserRepository

    fun addService(service: ServiceInputDto): ServiceOutputDto {
        val company = companyServices.getCompany(service.company)
        if(company.isEmpty) throw CompanyNotFound()
        val serv = ServiceDB(
            name = service.serviceName,
            duration = Time.valueOf(service.duration),
            numberMax = service.numberMax,
            price = service.price,
            company= company.get()
        )
        val savedService = serviceRepository.save(serv)
        return ServiceOutputDto(savedService)
    }

    fun getServiceById(id: Int): ServiceOutputDto {
        val serv = serviceRepository.getServiceDBById(id)
        return ServiceOutputDto(serv!!)
    }

    fun getAvailableEmployees(id: Int, hourBegin: String, hourEnd: String, date: String): List<UserOutputDto>{
        val hb = Time.valueOf(hourBegin)
        val he = Time.valueOf(hourEnd)
        val d = Date.valueOf(date)
        val users = userRepository.getAvailableEmployeesByService(id, d, hb, he)
        return users.map { UserOutputDto(it) }
    }

    fun updatePrice(idService: Int,price: Double):Long{
        return serviceRepository.updatePrice(idService,price)
    }

    fun updateDuration(idService: Int,duration: String): Duration {
        return serviceRepository.updateDuration(idService,Time.valueOf(duration))
    }

    fun delete(serviceDB: ServiceDB){
        serviceRepository.delete(serviceDB)
    }
    fun availableServicesByDay(companyId: Int, day: String,  beginHour: Time, endHour: Time){
        
    }

}