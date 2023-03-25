package backend.database

import backend.domain.Company
import backend.domain.Schedule
import backend.model.repository.interfaces.ICompanyRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals

@SpringBootTest(classes = [TestConfig::class])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DatabaseTests {

    lateinit var entityManager: TestEntityManager
    @Autowired
    lateinit var companyRepository: ICompanyRepository

    @Test
    fun `add a company`(){
        val com = Company(
            nif = "250407671",
            phoneNumber="933280527",
            address = "Rua Ferreira da Costa",
            compName = "Maf,Lda",
            compType = "Cabeleireiro",
            description = "Cabeleireira ",
            Schedule()
        )

        val company = companyRepository.insert(com)

        val get = companyRepository.select(company!!)

        assertEquals(company,get.id)


    }

    /*@Test
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

    }*/


}