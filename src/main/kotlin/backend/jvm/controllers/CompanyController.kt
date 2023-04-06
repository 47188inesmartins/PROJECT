package backend.jvm.controllers
import backend.jvm.model.Company
import backend.jvm.services.CompanyServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
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
    fun findAllById(@PathVariable id: Int): Optional<Company> {
        return companyServices.getCompany(id)
    }

    @GetMapping
    fun findCompanyByNif(@RequestParam nif: String): Company {
        return companyServices.findCompanyByNif(nif)
    }
}