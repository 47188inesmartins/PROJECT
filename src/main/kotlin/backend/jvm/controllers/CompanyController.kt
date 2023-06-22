package backend.jvm.controllers

import kotlinx.serialization.json.Json
import backend.jvm.model.*
import backend.jvm.services.CompanyServices
import backend.jvm.services.UserServices
import backend.jvm.services.dto.*
import backend.jvm.utils.RoleManager
import backend.jvm.utils.errorHandling.*
import jakarta.persistence.EntityNotFoundException
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import java.util.*


@RestController
@RequestMapping(("/company"))
class CompanyController {

    @Autowired
    lateinit var companyServices: CompanyServices

    @Autowired
    lateinit var userServices: UserServices

    @GetMapping("/search")
    fun searchForCompany(@RequestParam search: String?): ResponseEntity<List<CompanyOutputDto>> {
        return try {
            val response = companyServices.getSearchedCompanies(search)
            ResponseEntity
                .status(HttpStatus.OK)
                .header("Content-Type","application/json")
                .body(response)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Application error", e)
        }
    }


    @PostMapping
    fun addCompany(
        @RequestBody company: CompanyInputDto
    ): ResponseEntity<CompanyOutputDto> {
        return try {
            val requestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
            val request = requestAttributes.request
            val bearerToken = request.getHeader("Authorization")?.removePrefix("Bearer ")
            val response = bearerToken?.let { companyServices.addCompany(it,company) }
            ResponseEntity
                .status(201)
                .body(response)
        } catch (e: Exception) {
            println("exception = $e")
            when(e) {
                is NifAlreadyExist -> throw ResponseStatusException(HttpStatus.CONFLICT, "Nif already exists", e)
                is InvalidNif -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid nif", e)
                is UserNotFound -> throw ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid User", e)
                else -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
            }
        }
    }

    @RoleManager(["MANAGER"])
    @PostMapping("/{cid}/employee")
    fun addEmployee(@PathVariable cid: Int, @RequestBody emails: UserEmails): ResponseEntity<CreatedUserOutput?> {
        return try {
            userServices.addEmployees(cid, emails.emails)
            ResponseEntity
                .status(200)
                .body(null)
        }catch (e: Exception) {
            println(e)
            when(e){
                is UserNotFound -> throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e)
                is CompanyNotFound -> throw ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found", e)
                is AlreadyEmployee -> throw ResponseStatusException(HttpStatus.CONFLICT, "Already Employee", e)
                is AlreadyCompanyManager -> throw ResponseStatusException(HttpStatus.CONFLICT, "Already Company Manager", e)
                else -> throw  ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid user",e)
            }
        }
    }


    @RoleManager(["MANAGER"])
    @DeleteMapping("/{id}")
    fun deleteCompany(@PathVariable id: Int): ResponseEntity<Boolean> {
        return try {
          val response = companyServices.deleteCompany(id)
            ResponseEntity
                .status(201)
                .body(response)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong", e)
        }
    }

    @ResponseBody
    @GetMapping("/{id}")
    fun getCompany(@PathVariable id: Int): ResponseEntity<CompanyOutputDto> {
        return try {
            val response = companyServices.getCompanyById(id)
            ResponseEntity.status(200)
                .body(response)
        } catch (e: Exception) {
            when(e){
                is EntityNotFoundException ->  throw ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found", e)
                else -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong", e)
            }
        }
    }

    @RoleManager(["MANAGER","EMPLOYEE"])
    @GetMapping("/nif")
    fun findCompanyByNif(@RequestParam nif: String): ResponseEntity<Company> {
        return try {
            val response = companyServices.getCompanyByNif(nif)
            ResponseEntity
                .status(200)
                .body(response)
        } catch (e: Exception) {
            when(e) {
                is CompanyNotFound -> throw ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found", e)
                else -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong", e)
            }
        }
    }

    @GetMapping("/{id}/services")
    fun getAllServices(@PathVariable id: Int): ResponseEntity<List<ServiceOutputDto>> {
        return try {
            val response = companyServices.getAllServices(id)
            ResponseEntity.status(200).body(response)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
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
            when(e) {
                is CompanyNotFound -> throw ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found", e)
                else -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong", e)
            }
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
            val response = companyServices.getAllAppointmentsByCompany(id)
            ResponseEntity.status(200).body(response)
        }catch (e: Exception){
            when(e) {
                is CompanyNotFound -> throw ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found", e)
                else -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong", e)
            }
        }
    }

    @RoleManager(["MANAGER","EMPLOYEE","CLIENT"])
    @GetMapping("/{id}/appointment")
    fun getAppointmentByDateAndHour(@PathVariable id: Int, @RequestParam date: String, hour: String): ResponseEntity<List<AppointmentOutputDto>> {
        return try {
            val response = companyServices.getAppointmentByCompanyAndDateAndHour(id,date,hour)
            ResponseEntity
                .status(200)
                .body(response)
        }catch (e: Exception){
            when(e) {
                is CompanyNotFound -> throw ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found", e)
                else -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong", e)
            }
        }
    }

    @GetMapping("/{id}/days")
    fun getOpenDays(@PathVariable id: Int): ResponseEntity<List<DayOutputDto>>{
        return try {
            val response = companyServices.getOpenDaysByCompany(id)
            ResponseEntity
                .status(200)
                .body(response)
        }catch (e: Exception){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
        }
    }

    @GetMapping("/{id}/vacation")
    fun getVacation(@PathVariable id: Int): ResponseEntity<List<VacationOutputDto>>{
        return try{
            val response = companyServices.getVacationByCompany(id)
            ResponseEntity.status(200).body(response)
        }catch(e: Exception){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
        }
    }

    @GetMapping
    fun getAllCompanies(): ResponseEntity<List<CompanyOutputDto>>{
        return try{
            val response = companyServices.getAllCompanies()
            ResponseEntity.status(200).body(response)
        }catch(e: Exception){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
        }
    }

    @GetMapping("/info")
    fun getCompanyByManager(@RequestHeader("Authorization") token: String,@RequestParam role: String): ResponseEntity<List<CompanyInfo>>{
        return try{
            val response = companyServices.getCompanyByUserAndRole(token.split(" ")[1], role)
            ResponseEntity
                .status(200)
                .body(response)
        }catch(e: Exception){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
        }
    }

    @RoleManager(["MANAGER","EMPLOYEE"])
    @GetMapping("{cid}/employees-profit")
    fun getAllEmployeesByCompanyAndMoney(@PathVariable cid: Int): ResponseEntity<List<Pair<UserInfo,Double>>>{
        return try {
            val response = companyServices.getAllEmployeesByCompanyAndMoney(cid)
            ResponseEntity
                .status(200)
                .body(response)
        }catch(e: Exception){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
        }
    }

    @RoleManager(["MANAGER","EMPLOYEE"])
    @GetMapping("{cid}/appointments-list")
    fun getAllAppointmentsByCompany(@PathVariable cid: Int): ResponseEntity<List<AppointmentInfoEmployeeEnd>>{
        return try {
            val response = companyServices.getAppointmentsByCompany(cid)
            ResponseEntity
                .status(200)
                .body(response)
        }catch(e: Exception){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
        }
    }

    //@RoleManager(["MANAGER","EMPLOYEE"])
    @PostMapping("/{cid}/upload")
    fun uploadImage(@PathVariable cid: Int, @RequestBody file: Array<MultipartFile>): ResponseEntity<String> {
        return try {
            companyServices.uploadPhoto(cid,file)
            ResponseEntity
                .status(200)
                .body("Upload done")
        }catch(e: Exception){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
        }
    }
    @RoleManager(["MANAGER","EMPLOYEE","CLIENT"])
    @GetMapping("/{cid}/employees")
    fun getEmployeesByCompany( @PathVariable cid: Int): ResponseEntity<List<UserOutputDto>> {
        return try {
            val employee = companyServices.getEmployeesByCompany(cid)
            ResponseEntity
                .status(200)
                .body(employee)
        }catch(e: Exception){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
        }
    }

    @RoleManager(["MANAGER"])
    @DeleteMapping("/{cid}/employees")
    fun removeEmployee( @PathVariable cid: Int, @RequestParam id: Int): ResponseEntity<String>{
        return try {
            val a = companyServices.removeEmployeeFromCompany(cid, id)
            ResponseEntity
                .status(200)
                .body("delete successful")
        }catch(e: Exception){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
        }
    }
}
