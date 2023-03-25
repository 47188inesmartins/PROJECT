package backend.domain

import jakarta.persistence.*

/**
 *
 * Calendar entity
 *
 * @property id the Calendar primary key
 * @property cid the Company that this Calendar belongs
 * @property weekDay days of the week
 * @property calendarState to check if the company is open or close
 *
 *
 */

@Entity
@Table(name = "calendar")
class Calendar {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Int? = null

    @ManyToOne
    @JoinColumn(name = "cid")
    val cid: Company

    @Column(name = "week_day")
    val weekDay: String

    @Column(name = "calendar_state")
    val calendarState: String


    constructor(
        cid: Company,
        week: String,
        calendar: String
    ){
        this.cid = cid
        this.weekDay = week
        this.calendarState = calendar
    }

    constructor(){
        this.cid = Company()
        this.weekDay = ""
        this.calendarState = ""
    }
}