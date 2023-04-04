package backend.jvm.controllers

import backend.jvm.model.Vacation
import backend.jvm.services.VacationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/vacation")
class VacationController {

    @Autowired
    lateinit var vacationServices:VacationService

    @PostMapping
    fun addVacation(@RequestBody vacation: Vacation):Vacation {
        return vacationServices.addVacation(vacation)
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