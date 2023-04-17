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
 * @property user company associated with appointment
 *
 */


@Entity
@Table(name = "appointment")
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user : User?



    constructor() {
        this.appHour = Time.valueOf("00:00:00")
        this.appDate = Date.valueOf("2000-01-01")
        this.schedule = Schedule()
        this.user = null
        this.service = ServiceDB()
    }

    constructor(appHour: Time, appDate : Date, scheduleId: Schedule, userId: User?, serviceDB: ServiceDB) {
        this.appHour = appHour
        this.appDate = appDate
        this.schedule = scheduleId
        this.user = userId
        this.service = serviceDB
    }
}
