package backend.database

import backend.jvm.dao.ScheduleDao
import backend.jvm.model.company.CompanyEntity
import backend.jvm.model.schedule.ScheduleEntity
import backend.jvm.utils.Geolocation
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class ScheduleDaoTest {

    @Autowired
    lateinit var entityManager: TestEntityManager

    @Autowired
    lateinit var scheduleRepository: ScheduleDao

    @Test
    fun `saveSchedule`(){
        val location = Geolocation(41.1579438,-8.629105299999999)
        val company = CompanyEntity("966123456",location,null, "111111111", "Rua do teste", "tester", "test", "testing", null, null, null)
        entityManager.persist(company)
        entityManager.flush()
        val sch = ScheduleEntity(company,null,null,null,null)
        entityManager.persist(sch)
        entityManager.flush()
        val schFound = scheduleRepository.getScheduleById(sch.id)
        Assertions.assertThat(schFound?.id!! > 0)
    }

    @Test
    fun `checkScheduleByCompany`(){
        val location = Geolocation(41.1579438,-8.629105299999999)
        val company = CompanyEntity("966123456",location,null, "111111111", "Rua do teste", "tester", "test", "testing", null, null, null)
        entityManager.persist(company)
        entityManager.flush()
        val sch = ScheduleEntity(company,null,null,null,null)
        entityManager.persist(sch)
        entityManager.flush()
        val schFound = scheduleRepository.getScheduleByCompany_Id(company.id)
        Assertions.assertThat(schFound?.id!! > 0)
    }

}