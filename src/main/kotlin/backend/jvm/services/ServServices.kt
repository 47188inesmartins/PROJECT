package backend.jvm.services

import backend.jvm.model.ServiceDay
import backend.jvm.dao.*
import backend.jvm.model.day.DayInputDto
import backend.jvm.model.service.*
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
    lateinit var serviceDao: ServiceDao
    @Autowired
    lateinit var companyDao : CompanyDao
    @Autowired
    lateinit var userDao : UserDao
    @Autowired
    lateinit var userCompanyDao : UserCompanyDao
    @Autowired
    lateinit var scheduleDao : ScheduleDao
    @Autowired
    lateinit var dayDao : DayDao
    @Autowired
    lateinit var serviceDayDao : ServiceDayDao


    override fun addService(servicesInput: ServiceInputList, companyId: Int): ServicesOutputList {
        val listService = servicesInput.services
        val schedule = scheduleDao.getScheduleByCompany_Id(companyId) ?: throw ScheduleNotFound()
        val days = dayDao.getDayByScheduleId(schedule.id)
        val company = companyDao.getReferenceById(companyId)

        listService.forEach { service ->
            val users = service.users?.map{ userDao.getReferenceById(it) }
            users?.forEach {
                if(userCompanyDao.findByCompanyAndUser(company, it) == null) throw InvalidUser()
            }
            val serv = service.mapToService(service,company,users)
            val savedService = serviceDao.save(serv)
            days.map { serviceDayDao.save(ServiceDay(it, savedService)) }
        }
        val listsServicesName = listService.map { it.serviceName }
        return  ServicesOutputList(listsServicesName)
    }

    override fun getServiceById(id: Int): ServiceOutputDto {
        val serv = serviceDao.getServiceDBById(id)
        return ServiceOutputDto(serv!!)
    }

    override fun changePrice(idService: Int,price: Double):Long{
        return serviceDao.updatePrice(idService,price)
    }

    override fun changeDuration(idService: Int,duration: String): Duration {
        return serviceDao.updateDuration(idService,Time.valueOf(duration))
    }

    override fun deleteService(serviceEntity: ServiceEntity){
        serviceDao.delete(serviceEntity)
    }

    @Transactional
    fun changeSchedule(id: Int, day: List<DayInputDto>){
        val days = day.map { dayDao.save(it.mapToDayDb(it, null, null))}
        val serv = serviceDao.getServiceDBById(id)?: throw ServiceNotFound()
        serviceDayDao.deleteAllByService_Id(id)
        days.map{ serviceDayDao.save(ServiceDay(it, serv)) }
    }
}