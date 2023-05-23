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
 * @property schedule schedule associated with appointment
 * @property userDB company associated with appointment
 *
 */


@Entity
@Table(name = "appointment")
@org.hibernate.annotations.NamedQuery(
    name = "getAppointmentById",
    query = "from Appointment where id = :id",
    cacheable = true,
    timeout = 1
)
class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @Column(name = "app_hour", nullable = false)
    val appHour : Time

    @Column(name = "app_date")
    @Temporal(TemporalType.DATE)
    val appDate: Date

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")  // nome da foreign key
    val schedule : Schedule

    @ManyToOne
    @JoinColumn(name = "service_id")
    val service: ServiceDB

    @ManyToMany
    @JoinTable(
        name = "appointment_user",
        joinColumns = [JoinColumn(name = "appointment_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )

    val usersDB : List<UserDB>?



    constructor() {
        this.appHour = Time.valueOf("00:00:00")
        this.appDate = Date.valueOf("2000-01-01")
        this.schedule = Schedule()
        this.usersDB = null
        this.service = ServiceDB()
    }

    constructor(appHour: Time, appDate : Date, scheduleId: Schedule, userDBId: List<UserDB>?, serviceDB: ServiceDB) {
        this.appHour = appHour
        this.appDate = appDate
        this.schedule = scheduleId
        this.usersDB = userDBId
        this.service = serviceDB
    }
}
