package backend.database

import backend.domain.Appointment
import backend.domain.Company
import backend.domain.Schedule
import backend.repository.AppointmentRepository
import backend.repository.CompanyRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.context.SpringBootTest
import java.sql.Time
import kotlin.test.assertEquals

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DatabaseTests {
    @Autowired
    lateinit var entityManager: TestEntityManager
    @Autowired
    lateinit var appointmentRepository: AppointmentRepository
    @Autowired
    lateinit var companyRepository: CompanyRepository

    @Test
    fun `Add an appointment`(){
        val company = entityManager.persist(
            Company(
                email = "grupo18@hotmail.com",
                password = "grupo18",
                compName = "Cabeleireira LeiLa",
                compType = "Cabeleireiro",
                description = "Cabeleireira LeiLa,cabelos,unhas,depilaçao e unhas")
        )

        val schedule = entityManager.persist(
            Schedule(
                beginHour = Time.valueOf("12:00:00"),
                endHour = Time.valueOf("12:00:00"),
                shcInterval = Time.valueOf("00:30:00"),
                duration = Time.valueOf("00:30:00") ,
                compId = 1
            )
        )
        val appointment = entityManager.persist(
            Appointment( appNumber =  3, appHour = "12:30", availability = "available",sid = 1, cid = 1)
        )

        val foundAppointment = appointmentRepository.add(appointment)


        assertEquals(appointment.appHour,foundAppointment?.appHour)

    }


    @Test
    fun `Get a company`(){
        val company = entityManager.persist(
            Company(email = "mafalda@hotmail.com",
                password = "Mafalda123",
                compName = "Schedule APP",
                compType = "IT",
                description = "Aplicacao para fazer marcacoes")
        )

        val company2 = companyRepository.get("mafalda@hotmail.com")
        company2?.let { assertEquals(company.description, it.description) }

    }


}