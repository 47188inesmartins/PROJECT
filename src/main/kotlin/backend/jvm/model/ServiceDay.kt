package backend.jvm.model

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
    val day: Day?

    @ManyToOne
    @MapsId("service")
    @JoinColumn(name = "service_id")
    val service: ServiceDB?


    constructor(){
        this.day = null
        this.service = null
    }

    constructor(day: Day, service: ServiceDB){
        this.day = day
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