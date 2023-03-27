package backend.domain

import jakarta.persistence.*
import java.sql.Date
import javax.persistence.Column


@Entity
@Table(name = "vacation")
class Vacation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Int? = null

    @Temporal(TemporalType.DATE)
    @Column(name = "date_begin")
    val dateBegin: Date

    @Temporal(TemporalType.DATE)
    @Column(name = "date_end")
    val dateEnd: Date

    @ManyToOne
    @JoinColumn(name = "sid")
    val schedule: Schedule


    constructor(){
        this.dateBegin = Date.valueOf("")
        this.dateEnd = Date.valueOf("")
        this.schedule = Schedule()
    }

    constructor(dateBegin: Date, dateEnd: Date, schedule: Schedule){
        this.dateBegin = dateBegin
        this.dateEnd = dateEnd
        this.schedule = schedule
    }

}