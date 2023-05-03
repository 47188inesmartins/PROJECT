package backend.jvm.services.interfaces

import backend.jvm.model.ServiceDB
import backend.jvm.services.dto.ServiceInputDto
import backend.jvm.services.dto.ServiceOutputDto
import backend.jvm.services.dto.UserOutputDto
import java.time.Duration

interface IServServices {
    fun addService(service: ServiceInputDto): ServiceOutputDto

    fun getServiceById(id: Int): ServiceOutputDto

    fun getAvailableEmployees(id: Int, hourBegin: String, hourEnd: String, date: String): List<UserOutputDto>

    fun updatePrice(idService: Int,price: Double):Long

    fun updateDuration(idService: Int,duration: String): Duration

    fun delete(serviceDB: ServiceDB)
}