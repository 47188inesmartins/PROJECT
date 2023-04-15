package backend.jvm.model

import jakarta.persistence.*
import java.sql.Time

@Entity
@Table(name = "sch_day")
class Day {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Int = 0

        @Temporal(TemporalType.TIME)
        @Column(name = "begin_hour")
        val beginHour: Time

        @Temporal(TemporalType.TIME)
        @Column(name = "end_hour")
        val endHour: Time

        @Temporal(TemporalType.TIME)
        @Column(name = "day_interval")
        val interval: Time

        @Column(name = "week_days")
        val weekDays: String

        @ManyToOne
        @JoinColumn(name = "schedule_id")
        val schedule : Schedule?

        constructor(){
            this.beginHour = Time.valueOf("00:00:00")
            this.endHour = Time.valueOf("00:00:00")
            this.interval = Time.valueOf("00:00:00")
            this.weekDays = "MON"
            this.schedule = null
        }

        constructor(beginHour : Time, endHour: Time, interval: Time, weekDays: String, sid: Schedule){
            this.beginHour = beginHour
            this.endHour = endHour
            this.interval = interval
            this.weekDays = weekDays
            this.schedule = sid
        }

        constructor(id: Int, beginHour : Time, endHour: Time, interval: Time, weekDays: String, sid: Schedule){
            this.id = id
            this.beginHour = beginHour
            this.endHour = endHour
            this.interval = interval
            this.weekDays = weekDays
            this.schedule = sid
        }
}