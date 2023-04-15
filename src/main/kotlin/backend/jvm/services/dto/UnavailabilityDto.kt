package backend.jvm.services.dto

import backend.jvm.model.User
import jakarta.persistence.*
import java.sql.Date
import java.sql.Time

data class UnavailabilityInputDto(
    val dateBegin: String,
    val dateEnd: String,
    val hourBegin: String,
    val hourEnd: String,
    val user: UserOutputDto
)

/*class UnavailabilityDB {

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
    val userId : User*/