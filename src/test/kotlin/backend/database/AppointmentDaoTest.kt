package backend.database

import backend.jvm.dao.AppointmentDao
import backend.jvm.dao.ScheduleDao
import backend.jvm.dao.ServiceDao
import backend.jvm.dao.UserDao
import backend.jvm.model.appointment.AppointmentEntity
import backend.jvm.model.company.CompanyEntity
import backend.jvm.model.day.DayEntity
import backend.jvm.model.schedule.ScheduleEntity
import backend.jvm.model.service.ServiceEntity
import backend.jvm.model.user.UserEntity
import backend.jvm.model.vacation.VacationEntity
import backend.jvm.utils.Geolocation
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull
import java.sql.Date
import java.sql.Time


@DataJpaTest
class AppointmentDaoTest {

    @Autowired
    lateinit var entityManager: TestEntityManager

    @Autowired
    lateinit var appointmentRepository: AppointmentDao

    @Autowired
    lateinit var scheduleRepository: ScheduleDao

    @Autowired
    lateinit var serviceRepository: ServiceDao



    @Test
    fun `saveAppointment`() {
        val location = Geolocation(41.1579438,-8.629105299999999)
        val company = CompanyEntity("966123456",location,null, "111111111", "Rua do teste", "tester", "test", "testing", null, null, null)
        entityManager.persist(company)
        entityManager.flush()
        val sch = ScheduleEntity(company,null,null,null,null)
        entityManager.persist(sch)
        entityManager.flush()
        val service = ServiceEntity("nome",Time.valueOf("00:20:20"),20.0,company,null)
        entityManager.persist(service)
        entityManager.flush()
        val app = AppointmentEntity(Time.valueOf("20:20:20"),Date.valueOf("2002-10-02"),sch,null,service)
        entityManager.persist(app)
        entityManager.flush()
        val appFound =appointmentRepository.findByIdOrNull(app.id)
        Assertions.assertThat(appFound?.id!! > 0)
    }


    @Test
    fun `getAppBySchedule`() {
        val location = Geolocation(41.1579438,-8.629105299999999)
        val company = CompanyEntity("966123456",location,null, "111111111", "Rua do teste", "tester", "test", "testing", null, null, null)
        entityManager.persist(company)
        entityManager.flush()
        val sch = ScheduleEntity(company,null,null,null,null)
        entityManager.persist(sch)
        entityManager.flush()
        val service = ServiceEntity("nome",Time.valueOf("00:20:20"),20.0,company,null)
        entityManager.persist(service)
        entityManager.flush()
        val app = AppointmentEntity(Time.valueOf("20:20:20"),Date.valueOf("2002-10-02"),sch,null,service)
        entityManager.persist(app)
        entityManager.flush()
        val appFound =appointmentRepository.getAppointmentsBySchedule(sch.id)
        Assertions.assertThat(appFound.isNotEmpty())
    }
}