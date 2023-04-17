package backend.jvm.controllers

import kotlinx.serialization.json.Json
import backend.jvm.model.*
import backend.jvm.services.CompanyServices
import backend.jvm.services.dto.CompanyAddress
import backend.jvm.services.dto.CompanyInputDto
import backend.jvm.services.dto.CompanyOutputDto
import backend.jvm.services.dto.ServiceOutputDto
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import org.apache.catalina.connector.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.sql.Time
import java.util.*

@RestController
@RequestMapping(("/company"))
class CompanyController {

    @Autowired
    lateinit var companyServices: CompanyServices

    @ResponseBody
    @PostMapping
    fun addCompany(@RequestBody company: CompanyInputDto): ResponseEntity<CompanyOutputDto> {
        return try {
            val response = companyServices.addCompany(company)
            ResponseEntity
                .status(201)
                .body(response)

        } catch (e: Exception) {
            println("Exception = $e")
            ResponseEntity
                .status(400)
                .body(null)
        }
    }

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

    /* @GetMapping("/{id}/employees")
    fun getAllEmployees(@PathVariable id: Int): List<Employee>{
        return companyServices.getAllEmployees(id)
    }*/

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
       } catch (e: Exception) {
            ResponseEntity
                .status(400)
                .body(e.toString())
        }
    }

}
/*
    @GetMapping("/{id}/appointments")
    fun getAllAppointments(@PathVariable id: Int): ResponseEntity<List<AppointmentResponse>>{
        return try {
            val servs = companyServices.getAllAppointments(id)
            val response = servs.map { AppointmentResponse(it.id, it.appHour, it.appDate, it.schedule.id, it.user?.id) }
            ResponseEntity.status(200).body(response)
        }catch (e: Exception){
            ResponseEntity.status(400)
                .body(null)
        }
    }

    @GetMapping("/{id}/appointment")
    fun getAppointment(@PathVariable id: Int, @RequestBody date: Date, @RequestBody time: Time): Appointment {
        return companyServices.getAppointment(id,date,time)
    }

    @GetMapping("/{id}/days")
    fun getOpenDays(@PathVariable id: Int): ResponseEntity<List<String>>{
        return try {
            val openDays = companyServices.getOpenDays(id)
           // /*val response = openDays.map { DayResponse(it.id, it.beginHour, it.endHour, it.interval, it.weekDays, it.sid?.id!!) }
                ResponseEntity.status(200).body(openDays)
        }catch (e: Exception){
            println("exception = $e")
            ResponseEntity.status(400)
                .body(null)
        }
    }

    @GetMapping("/{id}/vacation")
    fun getVacation(@PathVariable id: Int): ResponseEntity<List<VacationResponse>>{
        return try{
            val vacationList = companyServices.getVacation(id)
            val response = vacationList.map { VacationResponse(it.id, it.dateBegin, it.dateEnd, it.schedule.id) }
            ResponseEntity.status(200).body(response)
        }catch(e: Exception){
            println("exception = $e")
            ResponseEntity.status(400).body(null)
        }
    }

    @PutMapping("/{id}/description")
    fun changeDescription(@PathVariable id: Int, @RequestBody address: String): Company{
        return companyServices.changeAddress(id, address)
    }

}*/