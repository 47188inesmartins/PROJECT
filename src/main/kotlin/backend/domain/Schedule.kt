package backend.domain

import jakarta.persistence.*
import java.sql.Time

@Entity(name = "Schedule")
@Table(name = "schedule")
class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    val id: Int?
    @Column(name = "begin_hour")
    val beginHour : Time
    @Column(name = "end_hour")
    val endHour : Time
    @Column(name = "shc_interval")
    val shcInterval : Time
    @Column(name = "duration")
    val duration : Time
    @OneToOne
    @JoinColumn(name = "comp_id")
    val compId : Company

    constructor(){
        this.id = null
        this.beginHour = Time.valueOf("")
        this.endHour = Time.valueOf("")
        this.shcInterval = Time.valueOf("")
        this.duration = Time.valueOf("")
        this.compId = Company()
    }

}