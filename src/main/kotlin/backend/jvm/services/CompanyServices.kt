package backend.jvm.services

import backend.jvm.model.*
import backend.jvm.repository.CompanyRepository
import backend.jvm.repository.ScheduleRepository
import backend.jvm.repository.ServiceRepository
import backend.jvm.repository.UserRepository
import backend.jvm.services.dto.CompanyInputDto
import backend.jvm.services.dto.CompanyOutputDto
import backend.jvm.services.dto.ServiceOutputDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Time
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

    fun addCompany(company: CompanyInputDto): CompanyOutputDto {
        if(company.nif.length != NIF_NUMBERS ) throw Exception("Invalid NIF number")
/*
* verificar se os users tem o role "cliente" e mudar para role employee quando
* sao adicionados
*
* */
        val schedule = company.schedule?.let { scheduleRepository.getReferenceById(it) }
        val users = company.users?.map { usersRepository.getById(it) }
        val services = company.service?.map { serviceRepository.getById(it) }
        val comp = companyRepository.save(
            Company(
                nif = company.nif,
                address = company.address,
                compName = company.name,
                compType = company.type,
                description = company.description,
                serviceDB = services,
                schedule = schedule,
                users = users
            )
        )
        return CompanyOutputDto(comp)
    }

    fun deleteCompany(id: Int): Boolean{
        companyRepository.deleteById(id)
        return true
    }

    fun getCompany(id: Int): Optional<Company> {
        return companyRepository.findById(id)
    }

    fun findCompanyByNif(nif: String): Company {
        return companyRepository.findCompanyByNif(nif)
    }

    /*fun getAllEmployees(id: Int): List<Employee>{
        return companyRepository.getAllEmployees(id)
    }*/

    fun getAllServices(id: Int): List<ServiceOutputDto>{
        return companyRepository.getAllServices(id).map { ServiceOutputDto(it) }
    }

    fun getAllAppointments(id: Int):List<Appointment>{
        return companyRepository.getAllAppointments(id)
    }

    fun getAppointment(id: Int, date: Date, time: Time): Appointment{
        return companyRepository.getAppointment(id, date, time)
    }

    fun getOpenDays(id: Int): List<String>{
        return companyRepository.getOpenDays(id)
    }

    fun getVacation(id: Int): List<Vacation>{
        /*a.map { element ->
            element[0] as Int
            element[1] as Time,
            element[]

        }*/
        return companyRepository.getVacation(id)
    }

    fun changeAddress(id: Int, address: String){
        companyRepository.changeAddress(id, address)
    }

    fun changeDescription(id: Int, description: String): Company{
        return companyRepository.changeDescription(id, description)
    }

}