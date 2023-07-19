package backend.jvm.model.vacation

import backend.jvm.model.schedule.ScheduleEntity
import jakarta.persistence.*
import java.sql.Date
import javax.persistence.Column


@SqlResultSetMapping(
    name = "VacationMapping",
    classes = [ConstructorResult(
        targetClass = VacationEntity::class,
        columns = [
            ColumnResult(name = "id", type = Long::class),
            ColumnResult(name = "date_begin", type = Date::class),
            ColumnResult(name = "date_end", type = Date::class),
            ColumnResult(name = "sid", type = Int::class)
        ]
    )]
)

@Entity
@Table(name = "vacation")
class VacationEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Int = 0

    @Temporal(TemporalType.DATE)
    @Column(name = "date_begin")
    val date_begin: Date

    @Temporal(TemporalType.DATE)
    @Column(name = "date_end")
    val date_end: Date

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    val schedule: ScheduleEntity


    constructor(){
        this.date_begin = Date.valueOf("2001-01-1")
        this.date_end =  Date.valueOf("2001-01-1")
        this.schedule = ScheduleEntity()
    }

    constructor(dateBegin: Date, dateEnd: Date, schedule: ScheduleEntity){
        this.date_begin = dateBegin
        this.date_end = dateEnd
        this.schedule = schedule
    }

}