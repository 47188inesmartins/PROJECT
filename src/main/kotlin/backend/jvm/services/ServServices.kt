package backend.jvm.services


import backend.jvm.model.ServiceDB
import backend.jvm.repository.CompanyRepository
import backend.jvm.repository.ServiceRepository
import backend.jvm.repository.UserRepository
import backend.jvm.services.dto.ServiceInputDto
import backend.jvm.services.dto.ServiceOutputDto
import backend.jvm.services.dto.UserOutputDto
import backend.jvm.services.interfaces.IServServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Date
import java.sql.Time
import java.time.Duration

@Service
class ServServices : IServServices {

    @Autowired
    lateinit var serviceRepository: ServiceRepository
    @Autowired
    lateinit var companyRepository : CompanyRepository
    @Autowired
    lateinit var userRepository : UserRepository

    override fun addService(service: ServiceInputDto): ServiceOutputDto {
        val company = companyRepository.getReferenceById(service.company)
        //if(company.isEmpty) throw CompanyNotFound()
        val serv = service.mapToService(service,company)
        val savedService = serviceRepository.save(serv)
        return ServiceOutputDto(savedService)
    }

    override fun getServiceById(id: Int): ServiceOutputDto {
        val serv = serviceRepository.getServiceDBById(id)
        return ServiceOutputDto(serv!!)
    }

    override fun getAvailableEmployees(id: Int, hourBegin: String, hourEnd: String, date: String): List<UserOutputDto>{
        val hb = Time.valueOf(hourBegin)
        val he = Time.valueOf(hourEnd)
        val d = Date.valueOf(date)
        val users = userRepository.getAvailableEmployeesByService(id, d, hb, he)
        return users.map { UserOutputDto(it) }
    }

    override fun updatePrice(idService: Int,price: Double):Long{
        return serviceRepository.updatePrice(idService,price)
    }

    override fun updateDuration(idService: Int,duration: String): Duration {
        return serviceRepository.updateDuration(idService,Time.valueOf(duration))
    }

    override fun delete(serviceDB: ServiceDB){
        serviceRepository.delete(serviceDB)
    }
}