package backend.jvm.services

import backend.jvm.model.*
import backend.jvm.repository.*
import backend.jvm.services.dto.*
import backend.jvm.services.interfaces.ICompanyServices
import backend.jvm.utils.errorHandling.CompanyNotFound
import backend.jvm.utils.errorHandling.InvalidNif
import backend.jvm.utils.errorHandling.NifAlreadyExist
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



    override fun addCompany(company: CompanyInputDto): CompanyOutputDto {
        if(company.nif.length != NIF_NUMBERS ) throw InvalidNif()
        if(companyRepository.findCompanyByNif(company.nif) != null) throw NifAlreadyExist()
        val schedule = company.schedule?.let { scheduleRepository.getReferenceById(it) }
        val users = company.users?.map { usersRepository.getReferenceById(it) }
        val services = company.service?.map { serviceRepository.getReferenceById(it) }
        val comp = companyRepository.save(
            company.mapToCompanyDto(company,services,schedule,users)
        )
       // scheduleRepository.save(Schedule(comp))
        return CompanyOutputDto(comp)
    }

    override fun deleteCompany(id: Int): Boolean{
        companyRepository.deleteById(id)
        return true
    }

    override fun getCompany(id: Int): Optional<Company> {
        val comp = companyRepository.findById(id)
        if(comp.isEmpty) throw CompanyNotFound()
        return comp
    }

    override fun findCompanyByNif(nif: String): Company? {
        return companyRepository.findCompanyByNif(nif)
    }

    override fun getAllServices(id: Int): List<ServiceOutputDto>{
        return serviceRepository.getAllServicesFromACompany(id).map { ServiceOutputDto(it) }
    }

    override fun getAllAppointments(id: Int):List<AppointmentOutputDto>{
        return appointmentRepository.getAllOnGoingAppointments(id).map { AppointmentOutputDto(it) }
    }

    override fun getAppointment(id: Int, date: String, hour: String): List<AppointmentOutputDto>{
        val d = Date.valueOf(date)
        val h = Time.valueOf(hour)
        return companyRepository.getAppointmentsByDateAndHour(id, d, h).map { AppointmentOutputDto(it) }
    }

    override fun getOpenDays(id: Int): List<DayOutputDto>{
        return dayRepository.getOpenDays(id).map { DayOutputDto(it) }
    }

    override fun getVacation(id: Int): List<VacationOutputDto>{
        return vacationRepository.getVacationsByCompany(id).map { VacationOutputDto(it) }
    }

    override fun changeAddress(id: Int, address: String){
        println("services = $address")
        companyRepository.changeAddress(id, address)
    }

    override fun changeDescription(id: Int, description: String): Company{
        return companyRepository.changeDescription(id, description)
    }


    fun getAllCompanies(): List<CompanyOutputDto>{
        return companyRepository.findAll().map{ CompanyOutputDto(it) }
    }

}