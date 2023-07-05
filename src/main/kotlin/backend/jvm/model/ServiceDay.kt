package backend.jvm.model

import backend.jvm.model.day.DayEntity
import backend.jvm.model.service.ServiceEntity
import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(name = "service_day")
class ServiceDay {

    @EmbeddedId
    var id: ServiceDayPK = ServiceDayPK()

    @ManyToOne
    @MapsId("day")
    @JoinColumn(name = "day_id")
    val dayEntity: DayEntity?

    @ManyToOne
    @MapsId("service")
    @JoinColumn(name = "service_id")
    val service: ServiceEntity?


    constructor(){
        this.dayEntity = null
        this.service = null
    }

    constructor(dayEntity: DayEntity, service: ServiceEntity){
        this.dayEntity = dayEntity
        this.service = service
    }
}


@Embeddable
class ServiceDayPK() : Serializable {

    var day: Int = 0
    var service: Int = 0

    constructor(day: Int, service: Int) : this() {
        this.day = day
        this.service = service
    }

}