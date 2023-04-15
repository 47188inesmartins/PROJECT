package backend.jvm.model

import jakarta.persistence.*
import java.sql.Time


@Entity
@Table(name = "service")
class ServiceDB{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @Column(name = "service_name")
    val name: String

    @Temporal(TemporalType.TIME)
    @Column(name = "duration")
    val duration: Time

    @Column(name = "number_max")
    val numberMax: Int

    @Column(name = "price")
    val price: Double

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    val companyId : Company

  /*  @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "service_appointment",
        joinColumns = [JoinColumn(name = "service_id")],
        inverseJoinColumns = [JoinColumn(name = "appointment_id")]
    )
    val appointment: List<Appointment>?*/


    @OneToMany(mappedBy = "service")
    val appointment: List<Appointment>?


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "service_day",
        joinColumns = [JoinColumn(name = "service_id")],
        inverseJoinColumns = [JoinColumn(name = "days_id")]
    )
    val day: List<Day>?

    @ManyToMany(mappedBy = "service", fetch = FetchType.LAZY)
    val user: List<User>?

    constructor(){
        this.name = ""
        this.duration = Time.valueOf("00:00:00")
        this.numberMax = 0
        this.price = 0.0
        this.companyId = Company()
        this.user = listOf()
        this.appointment = listOf()
        this.day = listOf()
    }

    constructor(name : String, duration: Time, numberMax: Int, price: Double, companyId: Company){
        this.name = name
        this.duration = duration
        this.numberMax = numberMax
        this.price = price
        this.companyId = companyId
        this.user = null
        this.appointment = null
        this.day = null
    }

}