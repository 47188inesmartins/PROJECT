package backend.Controllers

import backend.jvm.controllers.CompanyController
import backend.jvm.services.AppointmentServices
import backend.jvm.services.CompanyServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest
class CompanyControllersTest(@Autowired val mockMvc: MockMvc) {


    @Autowired
    lateinit var companyServices: CompanyServices

    @Test
    fun getExistentCompanyWithoutAuth(){
        mockMvc.perform(get("/company/1"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized);
    }
  /*  @Test
    fun `create a Company`(){
            val managerUser = UserInputDto(
                "managerTest@gmail.com",
                "testpass@123",
                "manager",
                "2000-01-01",
                null,
                null,
                null,
                null,
                null
            )
            val addUser = userServices.addUser(managerUser)
            val company = CompanyInputDto(
                "12345678",
                "rua adelaide",
                "corte e costura",
                "cabeleireiro",
                "cabeleireiro",
                null,
                null
            )
        val jsonObject = objectMapper.writeValueAsString(company)
        mockMvc.perform(
            MockMvcRequestBuilders.post("/company")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }*/
}


