package backend.jvm.controllers

import kotlinx.serialization.json.Json
import backend.jvm.model.*
import backend.jvm.services.CompanyServices
import backend.jvm.services.dto.*
import backend.jvm.utils.RoleManager
import backend.jvm.utils.errorHandling.NifAlreadyExist
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping(("/company"))
class CompanyController {

    @Autowired
    lateinit var companyServices: CompanyServices


    @RoleManager(["CLIENT"])
    @PostMapping
    fun addCompany(@RequestBody company: CompanyInputDto): ResponseEntity<CompanyOutputDto> {
        return try {
            val response = companyServices.addCompany(company)
            ResponseEntity
                .status(201)
                .body(response)
        } catch (e: Exception) {
            println("ex= $e")
            when(e){
                is NifAlreadyExist -> throw ResponseStatusException(HttpStatus.CONFLICT, "Nif already exists", e)
                else -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Nif is invalid", e)
            }

        }
    }

    @RoleManager(["MANAGER"])
    @DeleteMapping("/{id}")
    fun deleteCompany(@PathVariable id: Int): Boolean {
        return companyServices.deleteCompany(id)
    }

    @ResponseBody
    @GetMapping("/{id}")
    fun getCompany(@PathVariable id: Int): ResponseEntity<Company> {
        return try {
            val response = companyServices.getCompany(id)
            ResponseEntity.status(200).body(response.get())
        } catch (e: Exception) {
            ResponseEntity.status(400).body(null)
        }
    }

    @RoleManager(["MANAGER","EMPLOYEE"])
    @GetMapping
    fun findCompanyByNif(@RequestParam nif: String): ResponseEntity<Company> {
        return try {
            val response = companyServices.findCompanyByNif(nif)
            ResponseEntity
                .status(200)
                .body(response)
        } catch (e: Exception) {
            ResponseEntity
                .status(400)
                .body(null)
        }
    }


    @GetMapping("/{id}/services")
    fun getAllServices(@PathVariable id: Int): ResponseEntity<List<ServiceOutputDto>> {
        return try {
            val response = companyServices.getAllServices(id)
            ResponseEntity.status(200).body(response)
        } catch (e: Exception) {
            ResponseEntity.status(400)
                .body(null)
        }
    }

    @RoleManager(["MANAGER"])
    @PutMapping("/{id}/address")
    fun changeAddress(@PathVariable id: Int, @RequestBody companyAddress: String): ResponseEntity<String> {
        return try {
            val json = Json.parseToJsonElement(companyAddress)
            val request = json.jsonObject["companyAddress"]?.jsonPrimitive?.content
                ?: return ResponseEntity
                    .status(400)
                    .body(null)

            companyServices.changeAddress(id, request)
            ResponseEntity
                .status(200)
                .body("The address of the company was updated")
        }catch (e: Exception) {
            ResponseEntity
                .status(400)
                .body(e.toString())
        }
    }
    @RoleManager(["MANAGER"])
    @PutMapping("/{id}/description")
    fun changeDescription(@PathVariable id: Int, @RequestBody address: String){
       TODO()
    //  return companyServices.changeAddress(id, address)
    }


    @RoleManager(["MANAGER","EMPLOYEE","CLIENT"])
    @GetMapping("/{id}/appointments")
    fun getAllOnGoingAppointments(@PathVariable id: Int): ResponseEntity<List<AppointmentOutputDto>>{
        return try {
            val response = companyServices.getAllAppointments(id)
            ResponseEntity.status(200).body(response)
        }catch (e: Exception){
            println("exception= $e")
            ResponseEntity.status(400)
                .body(null)
        }
    }

    @RoleManager(["MANAGER","EMPLOYEE","CLIENT"])
    @GetMapping("/{id}/appointment")
    fun getAppointmentByDateAndHour(@PathVariable id: Int, @RequestParam date: String, hour: String): ResponseEntity<List<AppointmentOutputDto>> {
        return try {
            val response = companyServices.getAppointment(id,date,hour)
            ResponseEntity
                .status(200)
                .body(response)
        }catch (e: Exception){
            ResponseEntity
                .status(400)
                .body(null)
        }
    }

    @GetMapping("/{id}/days")
    fun getOpenDays(@PathVariable id: Int): ResponseEntity<List<DayOutputDto>>{
        return try {
            val response = companyServices.getOpenDays(id)
            ResponseEntity
                .status(200)
                .body(response)
        }catch (e: Exception){
            println("exception = $e")
            ResponseEntity.status(400)
                .body(null)
        }
    }

    @GetMapping("/{id}/vacation")
    fun getVacation(@PathVariable id: Int): ResponseEntity<List<VacationOutputDto>>{
        return try{

            val response = companyServices.getVacation(id)
            ResponseEntity.status(200).body(response)
        }catch(e: Exception){
            println("exception = $e")
            ResponseEntity.status(400).body(null)
        }
    }

}
