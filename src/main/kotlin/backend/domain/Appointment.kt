package backend.domain

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
    val id: Int?
    @Column(name = "number_app_people")
    val numberAppPeople: Int
    @Column(name = "app_hour")
    val appHour : Time
    @Column(name = "availability")
    val availability : String
    @ManyToOne
    @JoinColumn(name = "sid")  // nome da foreign key
    val sid : Schedule
    @OneToOne
    @JoinColumn(name = "cid")
    val cid : Company

    init {
        this.id = null
        this.numberAppPeople = 0
        this.appHour = Time.valueOf("")
        this.availability = "unavailable"
        this.sid = Schedule()
        this.cid = Company()
    }

    fun getId(): Int? = this.id

    fun getNumberAppPeople(): Int = this.numberAppPeople

    fun getAppHour(): Time = this.appHour

    fun getAvailability(): String = this.availability

    fun getSid(): Schedule = this.sid

    fun getCid(): Company = this.cid

}
