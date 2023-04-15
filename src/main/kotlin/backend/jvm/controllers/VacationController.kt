package backend.jvm.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.sql.Date


@RestController
@RequestMapping("/vacation")
class VacationController {

  /*  @Autowired
    lateinit var vacationServices:VacationService

    @PostMapping
    fun addVacation(@RequestBody vacation: Vacation): ResponseEntity<VacationResponse> {
        return try {
            val vac = vacationServices.addVacation(vacation)
            val response = VacationResponse(vac.id, vac.dateBegin, vac.dateEnd, vac.schedule.id)
            ResponseEntity.status(201).body(response)
        }catch (e: Exception){
            ResponseEntity.status(400).body(null)
        }
    }

    @GetMapping("/{id}")
    fun getVacation(@PathVariable id: Int): ResponseEntity<VacationResponse>{
        return try{
            val vac = vacationServices.getVacation(id)
            val response = VacationResponse(vac.id, vac.dateBegin, vac.dateEnd, vac.schedule.id)
            ResponseEntity.status(200).body(response)
        }catch (e: Exception){
            ResponseEntity.status(400).body(null)
        }
    }

    @DeleteMapping
    fun deleteVacation(@RequestBody vacation: Vacation){
        vacationServices.deleteVacation(vacation)
    }

    @PutMapping("/{id}/date-begin")
    fun updateBeginDate(@PathVariable id: Int, @RequestParam date: String): ResponseEntity<Date> {
        return try {
            ResponseEntity.status(200).body(vacationServices.changeBeginDate(id,date))
        }catch (e: Exception){
            println("exception = $e")
            ResponseEntity.status(400).body(null)
        }
    }

    @PutMapping("/{id}/date-end")
    fun updateEndDate(@PathVariable id: Int, @RequestParam date: String): ResponseEntity<Date> {
        return try {
            ResponseEntity.status(200).body(vacationServices.changeEndDate(id,date))
        }catch (e: Exception){
            ResponseEntity.status(400).body(null)
        }
    }
*/
}