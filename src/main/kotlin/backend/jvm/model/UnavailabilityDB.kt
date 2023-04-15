package backend.jvm.model

import backend.jvm.utils.getCurrentDate
import backend.jvm.utils.getCurrentTime
import jakarta.persistence.*
import java.sql.Date
import java.sql.Time


@Entity
@Table(name = "unavailability")
class UnavailabilityDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @Temporal(TemporalType.DATE)
    @Column(name = "date_begin")
    val dateBegin: Date

    @Temporal(TemporalType.DATE)
    @Column(name = "date_end")
    val dateEnd: Date

    @Column(name = "hour_begin")
    val hourBegin : Time

    @Column(name = "hour_end")
    val hourEnd : Time

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val userId : User

    constructor(){
        this.dateBegin = Date.valueOf(getCurrentDate())
        this.dateEnd = Date.valueOf(getCurrentDate())
        this.hourBegin = Time.valueOf(getCurrentTime())
        this.hourEnd = Time.valueOf(getCurrentTime())
        this.userId = User()
    }

    constructor(dateBegin: Date,dateEnd: Date,hourBegin: Time,hourEnd: Time,user: User){
        this.dateBegin = dateBegin
        this.dateEnd = dateEnd
        this.hourBegin = hourBegin
        this.hourEnd = hourEnd
        this.userId = user
    }

}