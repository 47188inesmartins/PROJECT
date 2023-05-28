package backend.Controllers

import backend.jvm.controllers.CompanyController
import org.junit.runner.RunWith
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@WebMvcTest(CompanyController::class)
class CompanyControllersTest {

  /*  @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var companyServices: CompanyServices

    @Test
    fun `create a Company`(){
            val com = Company(
                    "12345678",
                    "rua adelaide",
                    "corte e costura",
                    "cabeleireiro",
                    "cabeleireiro",
                listOf(),
                null,
                listOf()
                )
        Mockito.`when`(companyServices.addCompany(com)).thenReturn(com)
        this.mockMvc.perform(post("/company"))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
    }*/
//}