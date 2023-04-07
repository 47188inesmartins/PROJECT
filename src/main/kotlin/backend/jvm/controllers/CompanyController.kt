package backend.jvm.controllers
import backend.jvm.model.*
import backend.jvm.services.CompanyServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.security.Provider.Service
import java.sql.Time
import java.util.*

@RestController
@RequestMapping(("/company"))
class CompanyController {

    @Autowired
    lateinit var companyServices: CompanyServices

    @ResponseBody
    @PostMapping
    fun addCompany(@RequestBody company: Company): Company {
        return companyServices.addCompany(company)
    }

    @DeleteMapping("/{id}")
    fun deleteCompany(@PathVariable id: Int): Boolean {
        return companyServices.deleteCompany(id)
    }

    @ResponseBody
    @GetMapping("/{id}")
    fun getCompany(@PathVariable id: Int): Optional<Company> {
        return companyServices.getCompany(id)
    }

    @GetMapping
    fun findCompanyByNif(@PathVariable nif: String): Company {
        return companyServices.findCompanyByNif(nif)
    }

   /* @GetMapping("/{id}/employees")
    fun getAllEmployees(@PathVariable id: Int): List<Employee>{
        return companyServices.getAllEmployees(id)
    }*/

    @GetMapping("/{id}/services")
    fun getAllServices(@PathVariable id: Int): List<Services>{
        return companyServices.getAllServices(id)
    }

    @GetMapping("/{id}/appointments")
    fun getAllAppointments(@PathVariable id: Int): List<Appointment>{
        return companyServices.getAllAppointments(id)
    }

    @GetMapping("/{id}/appointment")
    fun getAppointment(@PathVariable id: Int, @RequestBody date: Date, @RequestBody time: Time): Appointment {
        return companyServices.getAppointment(id,date,time)
    }

    @GetMapping("/{id}/days")
    fun getOpenDays(@PathVariable id: Int): List<Day>{
        return companyServices.getOpenDays(id)
    }

    @GetMapping("/{id}/vacation")
    fun getVacation(@PathVariable id: Int): List<Vacation>{
        return companyServices.getVacation(id)
    }

    @PutMapping("/{id}/address")
    fun changeAddress(@PathVariable id: Int, @RequestBody address: String): Company{
        return companyServices.changeAddress(id, address)
    }

    @PutMapping("/{id}/description")
    fun changeDescription(@PathVariable id: Int, @RequestBody address: String): Company{
        return companyServices.changeAddress(id, address)
    }

}