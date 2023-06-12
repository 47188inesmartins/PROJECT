package backend.jvm.model

import jakarta.persistence.*
import java.sql.Time

@Entity
@Table(name = "sch_day")

@org.hibernate.annotations.NamedQuery(
    name = "getDayById",
    query = "from Day where id = :id",
    cacheable = true,
    timeout = 1
)

@org.hibernate.annotations.NamedQuery(
    name = "getDayBySchedule",
    query = "from Day where schedule = :schedule",
    cacheable = true,
    timeout = 1
)

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
        @Column(name = "interval_end")
        val intervalEnd: Time?

        @Temporal(TemporalType.TIME)
        @Column(name = "interval_begin")
        val intervalBegin: Time?

        @Column(name = "week_days")
        val weekDays: String

        @ManyToOne
        @JoinColumn(name = "schedule_id")
        val schedule : Schedule?

        @ManyToMany
        @JoinTable(
            name = "SERVICE_DAY",
            joinColumns = [JoinColumn(name = "day_id")],
            inverseJoinColumns = [JoinColumn(name = "service_id")]
        )
        val service: List<ServiceDB>?

        constructor(){
            this.beginHour = Time.valueOf("00:00:00")
            this.endHour = Time.valueOf("00:00:00")
            this.intervalBegin = Time.valueOf("00:00:00")
            this.intervalEnd = Time.valueOf("00:00:00")
            this.weekDays = "MON"
            this.schedule = null
            this.service = null
        }

        constructor(beginHour : Time, endHour: Time, intervalBegin: Time?, intervalEnd: Time?, weekDays: String, schedule: Schedule?, service: ServiceDB?){
            this.beginHour = beginHour
            this.endHour = endHour
            this.intervalEnd = intervalEnd
            this.intervalBegin = intervalBegin
            this.weekDays = weekDays
            this.schedule = schedule
            this.service = service
        }
}