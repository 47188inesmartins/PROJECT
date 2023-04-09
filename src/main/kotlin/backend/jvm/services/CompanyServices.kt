package backend.jvm.services

import backend.jvm.model.*
import backend.jvm.repository.CompanyRepository
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

    fun addCompany(company: Company): Company {
        if(company.nif.length != NIF_NUMBERS ) throw Exception("Invalid NIF number")
        return companyRepository.save(company)
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

    fun getAllServices(id: Int): List<Services>{
        return companyRepository.getAllServices(id)
    }

    fun getAllAppointments(id: Int):List<Appointment>{
        return companyRepository.getAllAppointments(id)
    }

    fun getAppointment(id: Int, date: Date, time: Time): Appointment{
        return companyRepository.getAppointment(id, date, time)
    }

    fun getOpenDays(id: Int): List<Day>{
        return companyRepository.getOpenDays(id)
    }

    fun getVacation(id: Int): List<Vacation>{
        return companyRepository.getVacation(id)
    }

    fun changeAddress(id: Int, address: String): Company{
        return companyRepository.changeAddress(id, address)
    }

    fun changeDescription(id: Int, description: String): Company{
        return companyRepository.changeDescription(id, description)
    }

}

/*
>#### PUT
> change company's address [/company/{id}] @RequestBody address
>
> change company's description [/company/{id}] @RequestBody description
*/