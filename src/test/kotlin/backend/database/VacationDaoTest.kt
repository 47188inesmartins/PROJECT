package backend.database

import backend.jvm.dao.CompanyDao
import backend.jvm.dao.VacationDao
import backend.jvm.model.company.CompanyEntity
import backend.jvm.model.schedule.ScheduleEntity
import backend.jvm.model.vacation.VacationEntity
import backend.jvm.utils.Geolocation
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import java.sql.Date

@DataJpaTest
class VacationDaoTest {

    @Autowired
    lateinit var entityManager: TestEntityManager

    @Autowired
    lateinit var vacationRepository: VacationDao

    @Autowired
    lateinit var companyRepository: CompanyDao

    @Test
    fun `Save_Vacation`(){
        val location = Geolocation(41.1579438,-8.629105299999999)
        val company = CompanyEntity("966123456",location,null, "111111111", "Rua do teste", "tester", "test", "testing", null, null, null)
        entityManager.persist(company)
        entityManager.flush()
        val sch = ScheduleEntity(company,null,null,null,null)
        entityManager.persist(sch)
        entityManager.flush()
        val vacation = VacationEntity(Date.valueOf("2024-01-1"), Date.valueOf("2024-01-20"), sch)
        entityManager.persist(vacation)
        entityManager.flush()

        val get = vacationRepository.findById(vacation.id).get()

        Assertions.assertThat(get.id > 0)
    }
}