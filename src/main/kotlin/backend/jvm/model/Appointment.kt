package backend.jvm.model

import jakarta.persistence.*
import java.sql.Time
import java.util.Date


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

    @Column(name = "app_date")
    @Temporal(TemporalType.DATE)
    val appDate: Date?


    @ManyToOne
    @JoinColumn(name = "sid")  // nome da foreign key
    val sid : Schedule?

    @ManyToOne
    @JoinColumn(name = "uid")
    val uid : User?

    @ManyToMany(mappedBy = "appointment")
    val services: List<Services>?

    constructor() {
        this.id = null
        this.numberAppPeople = 0
        this.appHour = Time.valueOf("00:00:00")
        this.availability = "unavailable"
        this.appDate = null
        this.uid = null
        this.sid = null
        this.services = null
    }

    constructor(numberAppPeople: Int, appHour: Time, availability: String, appDate : Date, sid: Schedule, uid: User, services: List<Services>) {
        this.numberAppPeople = numberAppPeople
        this.appHour = appHour
        this.availability = availability
        this.appDate = appDate
        this.sid = sid
        this.uid = uid
        this.services = services
    }

}
