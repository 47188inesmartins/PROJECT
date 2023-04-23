package backend.jvm.model

import jakarta.persistence.*

@Entity(name = "Schedule")
@Table(name = "schedule")
@org.hibernate.annotations.NamedQuery(
    name = "getScheduleById",
    query = "from Schedule where id = :id",
    cacheable = true,
    timeout = 1
)
class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int = 0

    @OneToOne
    @JoinColumn(name = "company_id", unique = true)
    val company : Company

    @OneToMany(mappedBy = "schedule")
    val appointment: List<Appointment>

    @OneToMany(mappedBy = "schedule")
    val day: List<Day>

    @OneToMany(mappedBy = "schedule")
    val vacation: List<Vacation>

    constructor(){
        this.company = Company()
        this.appointment = listOf()
        this.day = listOf()
        this.vacation = listOf()
    }

    constructor(companyId: Company,appointment: List<Appointment>,day: List<Day>,vacation: List<Vacation>){
        this.company = companyId
        this.appointment = appointment
        this.day = day
        this.vacation = vacation

    }
}