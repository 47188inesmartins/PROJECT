package backend.jvm.model.service

import backend.jvm.model.user.UserEntity
import backend.jvm.model.appointment.AppointmentEntity
import backend.jvm.model.company.CompanyEntity
import backend.jvm.model.day.DayEntity
import jakarta.persistence.*
import java.sql.Time




@Entity(name = "ServiceEntity")
@Table(name = "service")
@org.hibernate.annotations.NamedQuery(
    name = "getServiceById",
    query = "from ServiceEntity where id = :id",
    cacheable = true,
    timeout = 1
)

class ServiceEntity{
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
    val numberMax: Int?

    @Column(name = "price")
    val price: Double

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    val company : CompanyEntity

    @OneToMany(mappedBy = "service")
    val appointment: List<AppointmentEntity>?

    @ManyToMany
    @JoinTable(
        name = "SERVICE_DAY",
        joinColumns = [JoinColumn(name = "service_id")],
        inverseJoinColumns = [JoinColumn(name = "day_id")]
    )
    val day: List<DayEntity>?

    @ManyToMany
    @JoinTable(
        name = "user_service",
        joinColumns = [JoinColumn(name = "service_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    val user: List<UserEntity>?

    constructor(){
        this.name = ""
        this.duration = Time.valueOf("00:00:00")
        this.numberMax = 0
        this.price = 0.0
        this.company = CompanyEntity()
        this.user = listOf()
        this.appointment = listOf()
        this.day = listOf()
    }

    constructor(name : String, duration: Time, numberMax: Int?, price: Double, companyEntity: CompanyEntity, users: List<UserEntity>?){
        this.name = name
        this.duration = duration
        this.numberMax = numberMax
        this.price = price
        this.company = companyEntity
        this.user = users
        this.appointment = null
        this.day = null
    }

}