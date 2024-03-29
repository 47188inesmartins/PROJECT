package backend.jvm.controllers

import backend.jvm.services.ScheduleServices
import backend.jvm.model.schedule.ScheduleInputDto
import backend.jvm.model.schedule.ScheduleOutputDto
import backend.jvm.utils.RoleManager
import jakarta.persistence.EntityNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException


@RestController
@RequestMapping("/schedule")
class ScheduleController {

    @Autowired
    lateinit var scheduleServices: ScheduleServices

    @RoleManager(["MANAGER"])
    @ResponseBody
    @PostMapping
    fun addNewSchedule(@RequestBody schedule: ScheduleInputDto): ResponseEntity<ScheduleOutputDto> {
        return try {
            val response = scheduleServices.addSchedule(schedule)
            ResponseEntity
                .status(201)
                .body(response)
        } catch (e: Exception) {
            when (e) {
                is EntityNotFoundException -> throw ResponseStatusException(HttpStatus.NOT_FOUND, "entity does not exists", e)
                else -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong", e)
            }
        }
    }
    @RoleManager(["MANAGER"])
    @DeleteMapping("/{id}")
    fun deleteSchedule(@PathVariable id:Int){
        return try{
            scheduleServices.deleteSchedule(id)
        }catch (e: Exception) {
           throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong", e)
        }
    }

    @RoleManager(["MANAGER"])
    @GetMapping("/{id}")
    fun getSchedule(@PathVariable id:Int): ScheduleOutputDto?{
          return scheduleServices.getScheduleById(id)
    }


  /*  @GetMapping("/{id}/open")
    fun getOpenDays(@PathVariable id:Int):ResponseEntity<List<DayOutputDto>>{
        return try{
            val response = scheduleServices.getOpenDaysB(id)
            ResponseEntity
                .status(200)
                .body(response)
        }catch (e: Exception){
            when (e) {
                is EntityNotFoundException -> throw ResponseStatusException(HttpStatus.NOT_FOUND, "entity does not exists", e)
                else -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong", e)
            }
        }
    }

    @GetMapping("/{id}/vacation")
    fun getVacationDays(@PathVariable id:Int): ResponseEntity<List<VacationOutputDto>>{
        return try{
            val response = scheduleServices.getVacation(id)
            ResponseEntity
                .status(200)
                .body(response)
        }catch (e: Exception){
            ResponseEntity
                .status(400)
                .body(null)
        }
    }*/

}