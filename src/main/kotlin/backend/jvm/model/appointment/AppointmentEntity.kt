package backend.jvm.model.appointment

import backend.jvm.model.schedule.ScheduleEntity
import backend.jvm.model.service.ServiceEntity
import backend.jvm.model.user.UserEntity
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


@Entity(name = "AppointmentEntity")
@Table(name = "appointment")
@org.hibernate.annotations.NamedQuery(
    name = "getAppointmentById",
    query = "from AppointmentEntity where id = :id",
    cacheable = true,
    timeout = 1
)
class AppointmentEntity {

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
    @JoinColumn(name = "schedule_id")
    val schedule : ScheduleEntity

    @ManyToOne
    @JoinColumn(name = "service_id")
    val service: ServiceEntity

    @ManyToMany
    @JoinTable(
        name = "appointment_user",
        joinColumns = [JoinColumn(name = "appointment_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )

    val user : List<UserEntity?>?



    constructor() {
        this.appHour = Time.valueOf("00:00:00")
        this.appDate = Date.valueOf("2000-01-01")
        this.schedule = ScheduleEntity()
        this.user = null
        this.service = ServiceEntity()
    }

    constructor(appHour: Time, appDate : Date, scheduleId: ScheduleEntity, userEntityId: List<UserEntity?>?, serviceEntity: ServiceEntity) {
        this.appHour = appHour
        this.appDate = appDate
        this.schedule = scheduleId
        this.user = userEntityId
        this.service = serviceEntity
    }
}
