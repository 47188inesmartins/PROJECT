package backend.jvm.controllers

import backend.jvm.services.VacationService
import backend.jvm.model.vacation.VacationInputDto
import backend.jvm.model.vacation.VacationOutputDto
import backend.jvm.utils.RoleManager
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.sql.Date


@RestController
@RequestMapping("/company/{cid}/vacation")
class VacationController {

     @Autowired
    lateinit var vacationServices: VacationService

    @RoleManager(["MANAGER"])
    @PostMapping
    fun addVacation(@RequestBody vacation: VacationInputDto, @PathVariable cid: Int): ResponseEntity<VacationOutputDto> {
        return try {
            val response = vacationServices.addVacation(vacation, cid)
            ResponseEntity
                .status(201)
                .body(response)
        }catch (e: Exception){
            ResponseEntity.status(400).body(null)
        }
    }

    @RoleManager(["MANAGER","EMPLOYEE","CLIENT"])
    @GetMapping("/{id}")
    fun getVacation(@PathVariable id: Int, @PathVariable cid: Int): ResponseEntity<VacationOutputDto>{
        return try{
            val response = vacationServices.getVacation(id)
            ResponseEntity
                .status(200)
                .body(response)
        }catch (e: Exception){
            ResponseEntity.status(400).body(null)
        }
    }

    @RoleManager(["MANAGER"])
    @DeleteMapping("/{id}")
    fun deleteVacation(@PathVariable  id: Int, @PathVariable cid: Int){
        vacationServices.deleteVacation(id)
    }

    @RoleManager(["MANAGER"])
    @PutMapping("/{id}/date-begin")
    fun updateBeginDate(@PathVariable id: Int, @RequestParam date: String, @PathVariable cid: Int): ResponseEntity<Date> {
        return try {
            val json = Json.parseToJsonElement(date)

            val request = json.jsonObject["date"]?.jsonPrimitive?.content
                ?: return ResponseEntity
                    .status(400)
                    .body(null)

            ResponseEntity.status(200).body(vacationServices.changeBeginDate(id,request))
        }catch (e: Exception){
            println("exception = $e")
            ResponseEntity.status(400).body(null)
        }
    }

    @RoleManager(["MANAGER"])
    @PutMapping("/{id}/date-end")
    fun updateEndDate(@PathVariable id: Int, @RequestParam date: String, @PathVariable cid: Int): ResponseEntity<Date> {
        return try {
            val json = Json.parseToJsonElement(date)
            val request = json.jsonObject["date"]?.jsonPrimitive?.content
                ?: return ResponseEntity
                    .status(400)
                    .body(null)
            ResponseEntity.status(200).body(vacationServices.changeEndDate(id,request))
        }catch (e: Exception){
            ResponseEntity.status(400).body(null)
        }
    }
}