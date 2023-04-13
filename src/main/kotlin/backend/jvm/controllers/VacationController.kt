package backend.jvm.controllers

import backend.jvm.controllers.json.VacationResponse
import backend.jvm.model.Vacation
import backend.jvm.services.VacationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/vacation")
class VacationController {

    @Autowired
    lateinit var vacationServices:VacationService

    @PostMapping
    fun addVacation(@RequestBody vacation: Vacation): ResponseEntity<VacationResponse> {
        return try {
            val vac = vacationServices.addVacation(vacation)
            val response = VacationResponse(vac.id, vac.dateBegin, vac.dateEnd, vac.sid.id)
            ResponseEntity.status(201).body(response)
        }catch (e: Exception){
            ResponseEntity.status(400).body(null)
        }
    }

    @GetMapping("/{id}")
    fun getVacation(@PathVariable id: Int): ResponseEntity<VacationResponse>{
        return try{
            val vac = vacationServices.getVacation(id)
            val response = VacationResponse(vac.id, vac.dateBegin, vac.dateEnd, vac.sid.id)
            ResponseEntity.status(200).body(response)
        }catch (e: Exception){
            ResponseEntity.status(400).body(null)
        }
    }

    @DeleteMapping
    fun deleteVacation(@RequestBody vacation: Vacation){
        vacationServices.deleteVacation(vacation)
    }

    @PutMapping("/date-begin/{id}")
    fun updateBeginDate(@PathVariable id: Int, @RequestParam date: String): Vacation {
        return vacationServices.changeBeginDate(id,date)
    }

    @PutMapping("/date-end/{id}")
    fun updateEndDate(@PathVariable id: Int, @RequestParam date: String): Vacation {
        return vacationServices.changeEndDate(id,date)
    }

}