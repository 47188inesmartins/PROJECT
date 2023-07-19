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
    lateinit var scheduleDao : ScheduleDao
    @Autowired
    lateinit var dayDao : DayDao
    @Autowired
    lateinit var serviceDayDao : ServiceDayDao
    @Autowired
    lateinit var dayService : DayServices


    override fun addService(servicesInput: ServiceInputList, companyId: Int): ServicesOutputList {

        val listService = servicesInput.services
        val schedule = scheduleDao.getScheduleByCompany_Id(companyId) ?: throw ScheduleNotFound()
        val days = dayDao.getDayByScheduleId(schedule.id)
        val company = companyDao.getReferenceById(companyId)

        val userIds = listService.map { it.first }.flatMap { it.users.orEmpty() }
        val existingUsers = userDao.findAllById(userIds)
        val existingUserIds = existingUsers.map { it.id }

        val invalidUserIds = userIds.filter { it !in existingUserIds }
        if (invalidUserIds.isNotEmpty()) {
            throw InvalidUser()
        }

        val services = listService.filter { it.second == null }.map { service ->
            val usersForService = service.first.users?.mapNotNull { userId -> existingUsers.find { it.id == userId } }
            service.first.mapToService(service.first, company, usersForService)
        }

        val savedServices = serviceDao.saveAll(services)
        val serviceDays = days.flatMap { day -> savedServices.map { service -> ServiceDay(day, service) }}
        serviceDayDao.saveAll(serviceDays)


        val specialScheduleServices = listService.filter { it.second != null }.map { service ->
            val usersForService = service.first.users?.mapNotNull { userId -> existingUsers.find { it.id == userId } }
            service.first.mapToService(service.first, company, usersForService)
            val savedDays = dayService.addOpenDays(service.second!!, companyId, null)
            val savedService = serviceDao.save(service.first.mapToService(service.first, company, usersForService))
            val serviceDays = savedDays.map { day -> ServiceDay(day, savedService) }
            serviceDayDao.saveAll(serviceDays)
        }


        val listsServicesName = listService.map { it.first.serviceName }
        return ServicesOutputList(listsServicesName)
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