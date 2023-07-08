package backend.jvm.services.interfaces

import backend.jvm.model.service.*
import java.time.Duration

interface IServServices {
    fun addService(servicesInput: ServiceInputList, companyId: Int): ServicesOutputList

    fun getServiceById(id: Int): ServiceOutputDto

   // fun getAvailableEmployees(id: Int, hourBegin: String, hourEnd: String, date: String): List<UserOutputDto>

    fun changePrice(idService: Int,price: Double):Long

    fun changeDuration(idService: Int,duration: String): Duration

    fun deleteService(serviceEntity: ServiceEntity)
}