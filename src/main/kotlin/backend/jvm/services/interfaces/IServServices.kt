package backend.jvm.services.interfaces

import backend.jvm.model.service.ServiceEntity
import backend.jvm.model.service.ServiceInputDto
import backend.jvm.model.service.ServiceOutputDto
import java.time.Duration

interface IServServices {
    fun addService(service: ServiceInputDto, companyId: Int): ServiceOutputDto

    fun getServiceById(id: Int): ServiceOutputDto

   // fun getAvailableEmployees(id: Int, hourBegin: String, hourEnd: String, date: String): List<UserOutputDto>

    fun changePrice(idService: Int,price: Double):Long

    fun changeDuration(idService: Int,duration: String): Duration

    fun deleteService(serviceEntity: ServiceEntity)
}