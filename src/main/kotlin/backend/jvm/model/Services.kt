package backend.jvm.model

import jakarta.persistence.*
import java.sql.Time


@Entity
@Table(name = "service")
class Services{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int? = null

    @Column(name = "service_name")
    val name: String

    @Temporal(TemporalType.TIME)
    @Column(name = "duration")
    val duration: Time

    @Column(name = "number_max")
    val numberMax: Int

    @Column(name = "price")
    val price: Double

    @ManyToOne
    @JoinColumn(name = "cid")
    val cid : Company?

    @ManyToMany
    @JoinTable(
        name = "service_appointment",
        joinColumns = [JoinColumn(name = "service_id")],
        inverseJoinColumns = [JoinColumn(name = "appointment_id")]
    )
    val appointment: List<Appointment>?

    @ManyToMany
    @JoinTable(
        name = "service_days",
        joinColumns = [JoinColumn(name = "id_service")],
        inverseJoinColumns = [JoinColumn(name = "id_days")]
    )
    val day: List<Day>?

    @ManyToMany(mappedBy = "services")
    val user: List<User>?

    constructor(){
        this.name = ""
        this.duration = Time.valueOf("00:00:00")
        this.numberMax = 0
        this.price = 0.0
        this.cid = null
        this.user = listOf()
        this.appointment = listOf()
        this.day = listOf()
    }

    constructor(name : String, duration: Time, numberMax: Int, price: Double, company: Company, user: List<User>, appointment: List<Appointment>,days: List<Day> ){
        this.name = name
        this.duration = duration
        this.numberMax = numberMax
        this.price = price
        this.cid = company
        this.user = user
        this.appointment = appointment
        this.day = days
    }
}