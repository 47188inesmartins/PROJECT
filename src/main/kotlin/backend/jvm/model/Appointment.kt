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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    val eid: Employee?

    @ManyToOne
    @JoinColumn(name = "sid")  // nome da foreign key
    val sid : Schedule?

    @ManyToOne
    @JoinColumn(name = "cid")
    val cid : Client?

    @ManyToMany(mappedBy = "appointment")
    val services: List<Services>?

    constructor() {
        this.id = null
        this.numberAppPeople = 0
        this.appHour = Time.valueOf("00:00:00")
        this.availability = "unavailable"
        this.eid = null
        this.sid = null
        this.cid = null
        this.services = null
    }

    constructor(numberAppPeople: Int, appHour: Time, availability: String, eid: Employee, sid: Schedule, cid: Client, services: List<Services>) {
        this.numberAppPeople = numberAppPeople
        this.appHour = appHour
        this.availability = availability
        this.eid = eid
        this.sid = sid
        this.cid = cid
        this.services = services
    }

}
