package backend.jvm.model

import jakarta.persistence.*
import java.sql.Time




@Entity
@Table(name = "service")
@org.hibernate.annotations.NamedQuery(
    name = "getServiceById",
    query = "from ServiceDB where id = :id",
    cacheable = true,
    timeout = 1
)

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
    val company : Company

    @OneToMany(mappedBy = "service")
    val appointment: List<Appointment>?

    @ManyToMany
    @JoinTable(
        name = "SERVICE_DAY",
        joinColumns = [JoinColumn(name = "service_id")],
        inverseJoinColumns = [JoinColumn(name = "day_id")]
    )
    val day: List<Day>?

    @ManyToMany
    @JoinTable(
        name = "user_service",
        joinColumns = [JoinColumn(name = "service_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    val userDB: List<UserDB>?

    constructor(){
        this.name = ""
        this.duration = Time.valueOf("00:00:00")
        this.numberMax = 0
        this.price = 0.0
        this.company = Company()
        this.userDB = listOf()
        this.appointment = listOf()
        this.day = listOf()
    }

    constructor(name : String, duration: Time, numberMax: Int, price: Double, company: Company, users: List<UserDB>){
        this.name = name
        this.duration = duration
        this.numberMax = numberMax
        this.price = price
        this.company = company
        this.userDB = users
        this.appointment = null
        this.day = null
    }

}