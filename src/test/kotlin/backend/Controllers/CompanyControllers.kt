package backend.Controllers

import backend.jvm.controllers.CompanyController
import backend.jvm.model.Company
import backend.jvm.services.CompanyServices
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@RunWith(SpringRunner::class)
@WebMvcTest(CompanyController::class)
class CompanyControllersTest {

    @Autowired
    lateinit var mockMvc: MockMvc


    @MockBean
    lateinit var companyServices: CompanyServices

    @Test
    fun `create a Company`(){
            val com = Company(
                    1,
                    "12345678",
                    "rua adelaide",
                    "corte e costura",
                    "cabeleireiro",
                    "so cabelos",
                )
        Mockito.`when`(companyServices.addCompany(com)).thenReturn(com)
        this.mockMvc.perform(post("/company"))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
    }
}