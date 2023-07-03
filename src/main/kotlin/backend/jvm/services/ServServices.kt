package backend.jvm.services

import backend.jvm.model.ServiceDB
import backend.jvm.model.ServiceDay
import backend.jvm.repository.*
import backend.jvm.services.dto.DayInputDto
import backend.jvm.services.dto.ServiceInputDto
import backend.jvm.services.dto.ServiceOutputDto
import backend.jvm.services.interfaces.IServServices
import backend.jvm.utils.errorHandling.InvalidUser
import backend.jvm.utils.errorHandling.ScheduleNotFound
import backend.jvm.utils.errorHandling.ServiceNotFound
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
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
    @Autowired
    lateinit var userCompanyRepository : UserCompanyRepository
    @Autowired
    lateinit var scheduleRepository : ScheduleRepository
    @Autowired
    lateinit var dayRepository : DayRepository
    @Autowired
    lateinit var serviceDayRepository : ServiceDayRepository


    override fun addService(service: ServiceInputDto, companyId: Int): ServiceOutputDto {
        val schedule = scheduleRepository.getScheduleByCompany_Id(companyId) ?: throw ScheduleNotFound()
        val days = dayRepository.getDayByScheduleId(schedule.id)
        val company = companyRepository.getReferenceById(companyId)
        val users = service.users?.map{ userRepository.getReferenceById(it) }
        users?.forEach {
            if(userCompanyRepository.findByCompanyAndUser(company, it) == null) throw InvalidUser()
        }
        val serv = service.mapToService(service,company,users)
        val savedService = serviceRepository.save(serv)
        days.map { serviceDayRepository.save(ServiceDay(it, savedService)) }
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

    @Transactional
    fun changeSchedule(id: Int, day: List<DayInputDto>){
        val days = day.map { dayRepository.save(it.mapToDayDb(it, null, null))}
        val serv = serviceRepository.getServiceDBById(id)?: throw ServiceNotFound()
        serviceDayRepository.deleteAllByService_Id(id)
        days.map{ serviceDayRepository.save(ServiceDay(it, serv)) }
    }
}