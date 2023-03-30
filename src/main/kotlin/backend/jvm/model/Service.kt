package backend.jvm.model

import jakarta.persistence.*
import java.sql.Time

/*
@Entity
@Table(name = "service")
class Service{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
    val company : Company

    @ManyToMany
    @JoinTable(
        name = "service_appointment",
        joinColumns = [JoinColumn(name = "service_id")],
        inverseJoinColumns = [JoinColumn(name = "appointment_id")]
    )
    val appointment: List<Appointment>


    @ManyToMany(mappedBy = "service")
    val employee: List<Employee>

    constructor(){
        this.name = ""
        this.duration = Time.valueOf("00:00:00")
        this.numberMax = 0
        this.price = 0.0
        this.company = Company()
        this.employee = listOf()
        this.appointment = listOf()
    }

    constructor(name : String, duration: Time, numberMax: Int, price: Double, company: Company, employee: List<Employee>, appointment: List<Appointment>){
        this.name = name
        this.duration = duration
        this.numberMax = numberMax
        this.price = price
        this.company = company
        this.employee = employee
        this.appointment = appointment
    }
}*/