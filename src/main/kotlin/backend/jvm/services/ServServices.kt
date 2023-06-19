package backend.jvm.services

import backend.jvm.model.ServiceDB
import backend.jvm.repository.CompanyRepository
import backend.jvm.repository.ServiceRepository
import backend.jvm.repository.UserCompanyRepository
import backend.jvm.repository.UserRepository
import backend.jvm.services.dto.DayInputDto
import backend.jvm.services.dto.ServiceInputDto
import backend.jvm.services.dto.ServiceOutputDto
import backend.jvm.services.dto.UserOutputDto
import backend.jvm.services.interfaces.IServServices
import backend.jvm.utils.errorHandling.InvalidUser
import backend.jvm.utils.errorHandling.UserNotFound
import org.apache.catalina.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import java.sql.Date
import java.sql.Time
import java.time.Duration
import java.util.*

@Service
class ServServices : IServServices {

    @Autowired
    lateinit var serviceRepository: ServiceRepository
    @Autowired
    lateinit var companyRepository : CompanyRepository
    @Autowired
    lateinit var userRepository : UserRepository
    @Autowired
    lateinit var userCompanyRepository : UserCompanyRepository

    override fun addService(service: ServiceInputDto, companyId: Int): ServiceOutputDto {
        val company = companyRepository.getReferenceById(companyId)
        val users = service.users?.map{ userRepository.getReferenceById(it) }
        users?.forEach {
            if(userCompanyRepository.findByCompanyAndUser(company, it) == null) throw InvalidUser()
        }
        val serv = service.mapToService(service,company,users)
        val savedService = serviceRepository.save(serv)
        return ServiceOutputDto(savedService)
    }

    override fun getServiceById(id: Int): ServiceOutputDto {
        val serv = serviceRepository.getServiceDBById(id)
        return ServiceOutputDto(serv!!)
    }

    override fun changePrice(idService: Int,price: Double):Long{
        return serviceRepository.updatePrice(idService,price)
    }

    override fun changeDuration(idService: Int,duration: String): Duration {
        return serviceRepository.updateDuration(idService,Time.valueOf(duration))
    }

    override fun deleteService(serviceDB: ServiceDB){
        serviceRepository.delete(serviceDB)
    }

    fun getServiceByCompanyId(company: Int): List<ServiceOutputDto>{
        val services = serviceRepository.getAllServicesFromACompany(company)
        if(services.isEmpty()) return emptyList()
        return services.map { ServiceOutputDto(it) }
    }

    fun changeSchedule(id: Int, day: List<DayInputDto>){
        //TODO()
    }
}