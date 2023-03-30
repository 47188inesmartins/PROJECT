package backend.jvm.model

import jakarta.persistence.*
/*
@Entity(name = "Schedule")
@Table(name = "schedule")
class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    val id: Int?

    @OneToOne
    @JoinColumn(name = "comp_id")
    val compIdSch : Company

    @OneToMany(mappedBy = "sid")
    val appointment: List<Appointment>

    @OneToMany(mappedBy = "sid")
    val day: List<Day>

    @OneToMany(mappedBy = "sid")
    val vacation: List<Vacation>

    constructor(){
        this.id = null
        this.compIdSch = Company()
        this.appointment = listOf()
        this.day = listOf()
        this.vacation = listOf()
    }
}*/