package backend.jvm.model

import jakarta.persistence.*
import java.sql.Time

@Entity
@Table(name = "sch_day")
class Day {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Int? = null

        @Temporal(TemporalType.TIME)
        @Column(name = "begin_hour")
        val beginHour: Time?

        @Temporal(TemporalType.TIME)
        @Column(name = "end_hour")
        val endHour: Time?

        @Temporal(TemporalType.TIME)
        @Column(name = "day_interval")
        val interval: Time?

        @Column(name = "week_days")
        val weekDays: String

        @ManyToOne
        @JoinColumn(name = "sid")
        val sid : Schedule?

        constructor(){
            this.beginHour = null
            this.endHour = null
            this.interval = null
            this.weekDays = "MON"
            this.sid = null
        }

        constructor(beginHour : Time, endHour: Time, interval: Time, weekDays: String, sid: Schedule){
            this.beginHour = beginHour
            this.endHour = endHour
            this.interval = interval
            this.weekDays = weekDays
            this.sid = sid
        }
}