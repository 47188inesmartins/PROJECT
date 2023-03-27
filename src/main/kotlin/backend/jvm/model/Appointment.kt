package backend.jvm.model

import jakarta.persistence.*
import java.sql.Time


/**
 *
 * Appointment entity
 *
 * @property id the appointment primary key
 * @property appNumberPeople represents the number of people scheduled for that appointment hour
 * @property appHour hour of the appointment
 * @property availability 'unavailable' if the max number of people have already scheduled
 * @property sid schedule associated with appointment
 * @property cid company associated with appointment
 *
 */


@Entity
@Table(name = "appointment")
class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    var id: Int? = null

    @Column(name = "number_app_people")
    val numberAppPeople: Int

    @Column(name = "app_hour")
    val appHour : Time

    @Column(name = "availability")
    val availability : String

    @ManyToOne
    @JoinColumn(name = "eid")
    val eid: Employee

    @ManyToOne
    @JoinColumn(name = "sid")  // nome da foreign key
    val sid : Schedule

    @ManyToOne
    @JoinColumn(name = "cid")
    val cid : Client

    @ManyToMany(mappedBy = "appointment")
    val service: List<Service>

    constructor() {
        this.id = null
        this.numberAppPeople = 0
        this.appHour = Time.valueOf("")
        this.availability = "unavailable"
        this.eid = Employee()
        this.sid = Schedule()
        this.cid = Client()
        this.service = listOf()
    }

    constructor(numberAppPeople: Int, appHour: Time, availability: String, eid: Employee, sid: Schedule, cid: Client, service: List<Service>) {
        this.numberAppPeople = numberAppPeople
        this.appHour = appHour
        this.availability = availability
        this.eid = eid
        this.sid = sid
        this.cid = cid
        this.service = service
    }

}
