package backend.jvm.model.unavailability

import backend.jvm.model.user.UserEntity
import backend.jvm.utils.getCurrentDate
import backend.jvm.utils.getCurrentTime
import jakarta.persistence.*
import java.sql.Date
import java.sql.Time


@Entity(name = "UnavailabilityEntity")
@Table(name = "unavailability")
@org.hibernate.annotations.NamedQuery(
    name = "getUnavailabilityById",
    query = "from UnavailabilityEntity where id = :id",
    cacheable = true,
    timeout = 1
)
class UnavailabilityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @Temporal(TemporalType.DATE)
    @Column(name = "date_begin")
    val dateBegin: Date

    @Temporal(TemporalType.DATE)
    @Column(name = "date_end")
    val dateEnd: Date?

    @Column(name = "hour_begin")
    val hourBegin : Time?

    @Column(name = "hour_end")
    val hourEnd : Time?

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val userEntity : UserEntity

    constructor(){
        this.dateBegin = getCurrentDate()
        this.dateEnd = getCurrentDate()
        this.hourBegin = getCurrentTime()
        this.hourEnd = getCurrentTime()
        this.userEntity = UserEntity()
    }

    constructor(dateBegin: Date, dateEnd: Date?, hourBegin: Time?, hourEnd: Time?, userEntity: UserEntity){
        this.dateBegin = dateBegin
        this.dateEnd = dateEnd
        this.hourBegin = hourBegin
        this.hourEnd = hourEnd
        this.userEntity = userEntity
    }

}