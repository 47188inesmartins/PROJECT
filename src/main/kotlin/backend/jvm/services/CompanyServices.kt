package backend.jvm.services

import backend.jvm.model.*
import backend.jvm.repository.*
import backend.jvm.services.dto.*
import backend.jvm.services.interfaces.ICompanyServices
import backend.jvm.utils.UserRoles
import backend.jvm.utils.errorHandling.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Time
import java.sql.Date
import java.util.*

@Service
class CompanyServices : ICompanyServices {

    companion object{
        const val NIF_NUMBERS = 9
    }

    @Autowired
    lateinit var companyRepository: CompanyRepository

    @Autowired
    lateinit var scheduleRepository: ScheduleRepository

    @Autowired
    lateinit var usersRepository: UserRepository

    @Autowired
    lateinit var serviceRepository: ServiceRepository

    @Autowired
    lateinit var appointmentRepository: AppointmentRepository

    @Autowired
    lateinit var dayRepository: DayRepository

    @Autowired
    lateinit var vacationRepository: VacationRepository

    @Autowired
    lateinit var userCompanyRepository: UserCompanyRepository


    override fun addCompany(token: String, company: CompanyInputDto): CompanyOutputDto {

        val managerUser = usersRepository.getUserByToken(UUID.fromString(token))?: throw UserNotFound()
        company.users?.add(managerUser.id)

        if(company.nif.length != NIF_NUMBERS ) throw InvalidNif()
        if(companyRepository.findCompanyByNif(company.nif) != null) throw NifAlreadyExist()

        val services = company.service?.map { serviceRepository.getReferenceById(it) }
        val companyDb = company.mapToCompanyDto(company, services, null)
        val comp = companyRepository.save(companyDb)

        userCompanyRepository.save(UserCompany(managerUser, comp, UserRoles.MANAGER.name))
        val schedule = Schedule(comp,null,null,null)
        scheduleRepository.save(schedule)

        return CompanyOutputDto(comp)
    }

    override fun deleteCompany(id: Int): Boolean{
        companyRepository.deleteById(id)
        return true
    }

    override fun getCompanyById(id: Int): CompanyOutputDto {
        val comp = companyRepository.getReferenceById(id)
        //if(comp == null) throw CompanyNotFound()
        return CompanyOutputDto(comp)
    }

    override fun getCompanyByNif(nif: String): Company? {
        return companyRepository.findCompanyByNif(nif) ?: throw CompanyNotFound()
    }

    override fun getAllServicesByCompany(id: Int): List<ServiceOutputDto>{
        return serviceRepository.getAllServicesFromACompany(id).map { ServiceOutputDto(it) }
    }

    override fun getAllAppointmentsByCompany(id: Int):List<AppointmentOutputDto>{
        companyRepository.findAllById(id) ?: throw CompanyNotFound()
        return appointmentRepository.getAllOnGoingAppointments(id).map { AppointmentOutputDto(it) }
    }

    override fun getAppointmentByCompanyAndDateAndHour(id: Int, date: String, hour: String): List<AppointmentOutputDto>{
        val d = Date.valueOf(date)
        val h = Time.valueOf(hour)
        companyRepository.findAllById(id) ?: throw CompanyNotFound()
        return companyRepository.getAppointmentsByDateAndHour(id, d, h).map { AppointmentOutputDto(it) }
    }

    override fun getOpenDaysByCompany(id: Int): List<DayOutputDto>{
        return dayRepository.getOpenDays(id).map { DayOutputDto(it) }
    }

    override fun getVacationByCompany(id: Int): List<VacationOutputDto>{
        return vacationRepository.getVacationsByCompany(id).map { VacationOutputDto(it) }
    }

    override fun changeAddress(id: Int, address: String){
        companyRepository.findAllById(id) ?: throw CompanyNotFound()
        companyRepository.changeAddress(id, address)
    }

    override fun changeDescription(id: Int, description: String): Company{
        return companyRepository.changeDescription(id, description)
    }


    override fun getAllCompanies(): List<CompanyOutputDto>{
        return companyRepository.findAll().map{ CompanyOutputDto(it) }
    }

    override fun getAllServices(id: Int): List<ServiceOutputDto>{
        return serviceRepository.getAllServicesFromACompany(id).map { ServiceOutputDto(it) }
    }

    fun getCompanyByUserAndRole(userId: String, role: String): List<CompanyInfo>{
        val user = usersRepository.getUserByToken(UUID.fromString(userId)) ?: throw InvalidCredentials()
        val companyRepository = companyRepository.getCompanyByUserIdAndRole(user.id,role)
        return companyRepository.map {
            CompanyInfo(it.id,it.name)
        }
    }


}