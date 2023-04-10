package backend.jvm.model

import jakarta.persistence.*
import java.sql.Time
import java.sql.Date


/**
 *
 * Appointment entity
 *
 * @property id the appointment primary key
 * @property appHour hour of the appointment
 * @property sid schedule associated with appointment
 * @property uid company associated with appointment
 *
 */


@Entity
@Table(name = "appointment")
class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    /*
    @Column(name = "number_app_people")
    @GeneratedValue
    var numberAppPeople: Int? = 1
    */

    @Column(name = "app_hour")
    val appHour : Time

    @Column(name = "app_date")
    @Temporal(TemporalType.DATE)
    val appDate: Date?

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sid")  // nome da foreign key
    val sid : Schedule?

    @ManyToOne
    @JoinColumn(name = "uid")
    val uid : User?

    @ManyToMany(mappedBy = "appointment")
    val services: List<Services>?

    constructor() {
        this.appHour = Time.valueOf("00:00:00")
        this.appDate = null
        this.sid = null
        this.uid = null
        this.services = null
    }

    constructor(appHour: Time, appDate : Date, sid: Schedule?, uid: User?, services: List<Services>?) {
        this.appHour = appHour
        this.appDate = appDate
        this.sid = sid
        this.uid = uid
        this.services = services
    }

}
