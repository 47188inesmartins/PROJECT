package backend.jvm.controllers

import backend.jvm.services.ScheduleServices
import backend.jvm.services.dto.DayOutputDto
import backend.jvm.services.dto.ScheduleInputDto
import backend.jvm.services.dto.ScheduleOutputDto
import backend.jvm.services.dto.VacationOutputDto
import backend.jvm.utils.RoleManager
import jakarta.persistence.EntityNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/schedule")
class ScheduleController {

    @Autowired
    lateinit var scheduleServices: ScheduleServices

    @RoleManager(["MANAGER"])
    @ResponseBody
    @PostMapping
    fun addNewSchedule(@RequestBody schedule: ScheduleInputDto): ResponseEntity<ScheduleOutputDto> {
        return try{
            val response = scheduleServices.addSchedule(schedule)
            ResponseEntity
                .status(201)
                .body(response)
        }catch (e: Exception){
            ResponseEntity
                .status(400)
                .body(null)
        }
    }

    @RoleManager(["MANAGER"])
    @DeleteMapping("/{id}")
    fun deleteSchedule(@PathVariable id:Int){
        scheduleServices.deleteSchedule(id)
    }

    @RoleManager(["MANAGER"])
    @GetMapping("/{id}")
    fun getSchedule(@PathVariable id:Int):ScheduleOutputDto?{
          return scheduleServices.getSchedule(id)
    }


    @GetMapping("/{id}/open")
    fun getOpenDays(@PathVariable id:Int):ResponseEntity<List<DayOutputDto>>{
        return try{
            val response = scheduleServices.getOpenDays(id)
            ResponseEntity
                .status(200)
                .body(response)
        }catch (e: Exception){
            ResponseEntity
                .status(400)
                .body(null)
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
    }

}