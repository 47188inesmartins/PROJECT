package backend.jvm.controllers
import backend.jvm.http.CompanyResponse
import backend.jvm.model.Company
import backend.jvm.services.CompanyServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(("/company"),produces = ["application/json"])
class CompanyController {

    @Autowired
    lateinit var companyServices: CompanyServices

    @PostMapping
    fun addCompany(@RequestBody company: Company): CompanyResponse {
        val response = companyServices.addCompany(company)
        return  CompanyResponse(
            response.nif,
            response.address,
            response.compName,
            response.compType,
            response.description
        )
    }

    @DeleteMapping("/{id}")
    fun deleteCompany(@PathVariable id: Int): Boolean {
        return companyServices.deleteCompany(id)
    }

    @GetMapping("/{id}")
    fun findAllById(@PathVariable id: Int): Company {
        return companyServices.findAllById(id)
    }

    @GetMapping
    fun findCompanyByNif(@RequestParam nif: String): Company {
        return companyServices.findCompanyByNif(nif)
    }
}