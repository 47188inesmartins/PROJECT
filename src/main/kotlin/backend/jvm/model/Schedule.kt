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
    @JoinColumn(name = "comp_id")
    val compIdSch : Company?

    @OneToMany(mappedBy = "sid")
    val appointment: List<Appointment>

    @OneToMany(mappedBy = "sid")
    val day: List<Day>

    @OneToMany(mappedBy = "sid")
    val vacation: List<Vacation>

    constructor(){
        this.compIdSch = null
        this.appointment = listOf()
        this.day = listOf()
        this.vacation = listOf()
    }

    constructor(compIdSch: Company,appointment: List<Appointment>,day: List<Day>,vacation: List<Vacation>){
        this.compIdSch = compIdSch
        this.appointment = appointment
        this.day = day
        this.vacation = vacation

    }
}