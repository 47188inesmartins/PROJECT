package backend.jvm.model

import jakarta.persistence.*

@Entity(name = "Schedule")
@Table(name = "schedule")
class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int = 0

    @OneToOne
    @JoinColumn(name = "company_id", unique = true)
    val companyId : Company

    @OneToMany(mappedBy = "scheduleId")
    val appointment: List<Appointment>

    @OneToMany(mappedBy = "scheduleId")
    val day: List<Day>

    @OneToMany(mappedBy = "scheduleId")
    val vacation: List<Vacation>

    constructor(){
        this.companyId = Company()
        this.appointment = listOf()
        this.day = listOf()
        this.vacation = listOf()
    }

    constructor(companyId: Company,appointment: List<Appointment>,day: List<Day>,vacation: List<Vacation>){
        this.companyId = companyId
        this.appointment = appointment
        this.day = day
        this.vacation = vacation

    }
}