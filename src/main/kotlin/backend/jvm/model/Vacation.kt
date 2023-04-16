package backend.jvm.model

import jakarta.persistence.*
import java.sql.Date
import javax.persistence.Column


@SqlResultSetMapping(
    name = "VacationMapping",
    classes = [ConstructorResult(
        targetClass = Vacation::class,
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
class Vacation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Int = 0

    @Temporal(TemporalType.DATE)
    @Column(name = "date_begin")
    val dateBegin: Date

    @Temporal(TemporalType.DATE)
    @Column(name = "date_end")
    val dateEnd: Date

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    val schedule: Schedule


    constructor(){
        this.dateBegin = Date.valueOf("2001-01-1")
        this.dateEnd =  Date.valueOf("2001-01-1")
        this.schedule = Schedule()
    }

    constructor(dateBegin: Date, dateEnd: Date, schedule: Schedule){
        this.dateBegin = dateBegin
        this.dateEnd = dateEnd
        this.schedule = schedule
    }

}
