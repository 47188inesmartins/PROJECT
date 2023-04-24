package backend.jvm.services.dto

import backend.jvm.model.*


/*
  @OneToOne
    @JoinColumn(name = "company_id", unique = true)
    val company : Company

    @OneToMany(mappedBy = "schedule")
    val appointment: List<Appointment>

    @OneToMany(mappedBy = "schedule")
    val day: List<Day>

    @OneToMany(mappedBy = "schedule")
    val vacation: List<Vacation>
 */
data class ScheduleInputDto(
    val companyId: Int,
    val appointment: List<Int>,
    val day: List<Int>,
    val vacation: List<Int>
    ){

    fun mapToSchedule(company: Company, appointment: List<Appointment>, day: List<Day>, vacation: List<Vacation> ): Schedule =
        Schedule(
                company,
                appointment,
                day,
                vacation
        )

}

data class ScheduleOutputDto(
    val id: Int,
    val companyId: Int,
    val appointment: List<Int>?,
    val day: List<Int>?,
    val vacation: List<Int>?
    ){
    constructor( scheduleDb: Schedule):this(
        id = scheduleDb.id,
        companyId = scheduleDb.company.id,
        appointment = scheduleDb.appointment?.map { it.id },
        day = scheduleDb.day?.map { it.id },
        vacation = scheduleDb.vacation?.map { it.id }
    )
}
