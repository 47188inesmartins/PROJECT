package backend.domain

import jakarta.persistence.*
import java.sql.Time

@Entity
@Table(name = "day")
class Day {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        @Column(name = "id")
        var id: Int? = null

        @Temporal(TemporalType.TIME)
        @Column(name = "begin_hour")
        val beginHour: Time

        @Temporal(TemporalType.TIME)
        @Column(name = "end_hour")
        val endHour: Time

        @Temporal(TemporalType.TIME)
        @Column(name = "interval")
        val interval: Time

        @Column(name = "week_days")
        val weekDays: String

        @ManyToOne
        @JoinColumn(name = "sid")
        val sid : Schedule?

        constructor(){
            this.beginHour = Time.valueOf("")
            this.endHour = Time.valueOf("")
            this.interval = Time.valueOf("")
            this.weekDays = ""
            this.sid = Schedule()
        }

        constructor(beginHour : Time, endHour: Time, interval: Time, weekDays: String, sid: Schedule){
            this.beginHour = beginHour
            this.endHour = endHour
            this.interval = interval
            this.weekDays = weekDays
            this.sid = sid
        }
}