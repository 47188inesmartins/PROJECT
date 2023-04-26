package backend.jvm.services

import backend.jvm.model.*
import backend.jvm.repository.*
import backend.jvm.services.dto.*
import backend.jvm.utils.errorHandling.CompanyNotFound
import backend.jvm.utils.errorHandling.InvalidNif
import backend.jvm.utils.errorHandling.NifAlreadyExist
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Time
import java.sql.Date
import java.util.*

@Service
class CompanyServices {
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


    /*
    * change role
    * no interceptor
    */

    fun addCompany(company: CompanyInputDto): CompanyOutputDto {
        if(company.nif.length != NIF_NUMBERS ) throw InvalidNif()
        if(companyRepository.findCompanyByNif(company.nif) != null) throw NifAlreadyExist()
        val schedule = company.schedule?.let { scheduleRepository.getReferenceById(it) }
        val users = company.users?.map { usersRepository.getReferenceById(it) }
        val services = company.service?.map { serviceRepository.getReferenceById(it) }
        val comp = companyRepository.save(
            Company(
                nif = company.nif,
                address = company.address,
                compName = company.name,
                compType = company.type,
                description = company.description,
                serviceDB = services,
                schedule = schedule,
                userDBS = users
            )
        )
       // scheduleRepository.save(Schedule(comp))
        return CompanyOutputDto(comp)
    }

    fun deleteCompany(id: Int): Boolean{
        companyRepository.deleteById(id)
        return true
    }

    fun getCompany(id: Int): Optional<Company> {
        val comp = companyRepository.findById(id)
        if(comp.isEmpty) throw CompanyNotFound()
        return comp
    }

    fun findCompanyByNif(nif: String): Company? {
        return companyRepository.findCompanyByNif(nif)
    }

    /*fun getAllEmployees(id: Int): List<Employee>{
        return companyRepository.getAllEmployees(id)
    }*/

    fun getAllServices(id: Int): List<ServiceOutputDto>{
        return serviceRepository.getAllServicesFromACompany(id).map { ServiceOutputDto(it) }
    }

    fun getAllAppointments(id: Int):List<AppointmentOutputDto>{
        return appointmentRepository.getAllOnGoingAppointments(id).map { AppointmentOutputDto(it) }
    }

    fun getAppointment(id: Int, date: String, hour: String): List<AppointmentOutputDto>{
        val d = Date.valueOf(date)
        val h = Time.valueOf(hour)
        return companyRepository.getAppointmentsByDateAndHour(id, d, h).map { AppointmentOutputDto(it) }
    }

    fun getOpenDays(id: Int): List<DayOutputDto>{
        return dayRepository.getOpenDays(id).map { DayOutputDto(it) }
    }

    fun getVacation(id: Int): List<VacationOutputDto>{
        return vacationRepository.getVacationsByCompany(id).map { VacationOutputDto(it) }
    }

    fun changeAddress(id: Int, address: String){
        println("services = $address")
        companyRepository.changeAddress(id, address)
    }

    fun changeDescription(id: Int, description: String): Company{
        return companyRepository.changeDescription(id, description)
    }

}