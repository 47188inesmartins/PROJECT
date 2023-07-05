package backend.jvm.model.schedule

import backend.jvm.model.vacation.VacationEntity
import backend.jvm.model.appointment.AppointmentEntity
import backend.jvm.model.company.CompanyEntity
import backend.jvm.model.day.DayEntity
import jakarta.persistence.*
import java.sql.Time

@Entity(name = "ScheduleEntity")
@Table(name = "schedule")
@org.hibernate.annotations.NamedQuery(
    name = "getScheduleById",
    query = "from ScheduleEntity where id = :id",
    cacheable = true,
    timeout = 1
)
class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int = 0

    @OneToOne
    @JoinColumn(name = "company_id", unique = true)
    val companyEntity : CompanyEntity

    @Column(name = "between_interval")
    val betweenInterval : Time?

    @OneToMany(mappedBy = "schedule")
    val appointmentEntity: List<AppointmentEntity>?

    @OneToMany(mappedBy = "schedule")
    val dayEntity: List<DayEntity>?

    @OneToMany(mappedBy = "schedule")
    val vacationEntity: List<VacationEntity>?

    constructor(){
        this.companyEntity = CompanyEntity()
        this.appointmentEntity = listOf()
        this.dayEntity = listOf()
        this.vacationEntity = listOf()
        this.betweenInterval = null
    }

    constructor(companyEntityId: CompanyEntity, appointmentEntity: List<AppointmentEntity>?, dayEntity: List<DayEntity>?, vacationEntity: List<VacationEntity>?, betweenInterval : Time?){
        this.companyEntity = companyEntityId
        this.appointmentEntity = appointmentEntity
        this.dayEntity = dayEntity
        this.vacationEntity = vacationEntity
        this.betweenInterval = betweenInterval
    }
}