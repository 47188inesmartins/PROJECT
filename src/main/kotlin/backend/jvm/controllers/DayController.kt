package backend.jvm.controllers

import backend.jvm.model.Day
import backend.jvm.services.DayServices
import backend.jvm.services.dto.DayInputDto
import backend.jvm.services.dto.DayOutputDto
import backend.jvm.utils.RoleManager
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.sql.Time


@RestController
@RequestMapping("/day")
class DayController {

    @Autowired
    lateinit var dayService:DayServices

    @RoleManager(["MANAGER"])
    @PostMapping
    fun addOpenDay(@RequestBody day: DayInputDto): ResponseEntity<DayOutputDto> {
        return try{
            val addedDay = dayService.addOpenDay(day)
            ResponseEntity.status(201).body(addedDay)
        }catch(e: Exception){

            ResponseEntity.status(400).body(null)
        }
    }

    @RoleManager(["MANAGER"])
    @DeleteMapping("/{id}")
    fun deleteOpenDay(@PathVariable id: Int): ResponseEntity<String> {
        return try{
            dayService.deleteDay(id)
            ResponseEntity.status(200)
                .body("Open day deleted")
        }catch(e: Exception){
            println("exception = $e")
            ResponseEntity
                .status(400)
                .body(e.toString())
        }
    }

    @RoleManager(["MANAGER"])
    @ResponseBody
    @PutMapping("/{id}/begin")
    fun updateBeginHour(@PathVariable id:Int,@RequestParam begin: String): ResponseEntity<Time> {
        return try{
            val json = Json.parseToJsonElement(begin)
            val request = json.jsonObject["begin"]?.jsonPrimitive?.content
                ?: return ResponseEntity
                    .status(400)
                    .body(null)
            val response = dayService.updateBeginHour(id,request)
            ResponseEntity.status(200)
                .body(response)
        }catch(e: Exception){
            println("exception = $e")
            ResponseEntity
                .status(400)
                .body(null)
        }
    }

    @RoleManager(["MANAGER"])
    @ResponseBody
    @PutMapping("/{id}/end")
    fun updateEndHour(@PathVariable id:Int,@RequestParam end: String): ResponseEntity<Time> {
        return try{
            val json = Json.parseToJsonElement(end)
            val request = json.jsonObject["end"]?.jsonPrimitive?.content
                ?: return ResponseEntity
                    .status(400)
                    .body(null)
            val response = dayService.updateEndHour(id,request)
            ResponseEntity.status(200)
                .body(response)
        }catch(e: Exception){
            println("exception = $e")
            ResponseEntity
                .status(400)
                .body(null)
        }
    }

}