package backend.jvm.controllers

import backend.jvm.services.DayServices
import backend.jvm.services.dto.DayInputDto
import backend.jvm.services.dto.DayOutputDto
import backend.jvm.utils.RoleManager
import backend.jvm.utils.errorHandling.InvalidOpenDay
import backend.jvm.utils.errorHandling.InvalidSchedule
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.sql.Time


@RestController
@RequestMapping("company/{cid}/day")
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
            when(e){
                is InvalidOpenDay -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid open day", e)
                is InvalidSchedule -> throw ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found", e)
                else -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
            }
        }
    }

    @GetMapping("/week-day")
    fun getScheduleByWeekDay(@RequestParam day: String, @PathVariable cid: Int): ResponseEntity<List<String>>{
        return try{
            val schedule = dayService.getScheduleByWeekDay(day, cid)
            ResponseEntity.status(200).body(schedule)
        }catch(e: Exception){
            when(e){
                is InvalidOpenDay -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid open day", e)
                is InvalidSchedule -> throw ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found", e)
                else -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
            }
        }
    }

    @RoleManager(["MANAGER"])
    @PostMapping("/all")
    fun addOpenDays(@RequestBody day: List<DayInputDto>, @PathVariable cid: Int, @RequestParam duration: String ): ResponseEntity<*> {
        return try{
            dayService.addOpenDays(day, cid, duration)
            ResponseEntity.status(201).body(null)
        }catch(e: Exception){
            when(e){
                is InvalidOpenDay -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid open day", e)
                is InvalidSchedule -> throw ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found", e)
                else -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
            }
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
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
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
            when(e){
                is InvalidOpenDay -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid open day", e)
                else -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
            }
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
            when(e){
                is InvalidOpenDay -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid open day", e)
                else -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
            }
        }
    }
}