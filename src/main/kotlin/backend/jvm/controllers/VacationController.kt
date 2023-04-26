package backend.jvm.controllers

import backend.jvm.services.VacationService
import backend.jvm.services.dto.VacationInputDto
import backend.jvm.services.dto.VacationOutputDto
import backend.jvm.utils.RoleManager
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.sql.Date


@RestController
@RequestMapping("/vacation")
class VacationController {

     @Autowired
    lateinit var vacationServices: VacationService

    @RoleManager(["manager"])
    @PostMapping
    fun addVacation(@RequestBody vacation: VacationInputDto): ResponseEntity<VacationOutputDto> {
        return try {
            val response = vacationServices.addVacation(vacation)
            ResponseEntity
                .status(201)
                .body(response)
        }catch (e: Exception){
            ResponseEntity.status(400).body(null)
        }
    }

    @RoleManager(["manager,employee,client"])
    @GetMapping("/{id}")
    fun getVacation(@PathVariable id: Int): ResponseEntity<VacationOutputDto>{
        return try{
            val response = vacationServices.getVacation(id)
            ResponseEntity
                .status(200)
                .body(response)
        }catch (e: Exception){
            ResponseEntity.status(400).body(null)
        }
    }

    @RoleManager(["manager"])
    @DeleteMapping("/{id}")
    fun deleteVacation(@PathVariable  id: Int){
        vacationServices.deleteVacation(id)
    }

    @RoleManager(["manager"])
    @PutMapping("/{id}/date-begin")
    fun updateBeginDate(@PathVariable id: Int, @RequestParam date: String): ResponseEntity<Date> {
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

    @RoleManager(["manager"])
    @PutMapping("/{id}/date-end")
    fun updateEndDate(@PathVariable id: Int, @RequestParam date: String): ResponseEntity<Date> {
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