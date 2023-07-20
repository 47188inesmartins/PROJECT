package backend.Controllers

import backend.jvm.controllers.CompanyController
import backend.jvm.services.CompanyServices
import backend.jvm.model.company.CompanyOutputDto
import backend.jvm.services.UserServices
import backend.jvm.utils.pipeline.AuthenticationsInterceptor
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@WebMvcTest(controllers = [CompanyController::class])
@ExtendWith(SpringExtension::class)
class CompanyEntityControllersTest(@Autowired val mockMvc: MockMvc) {

    @Autowired
    private lateinit var mock : MockMvc

    @MockBean
    lateinit var companyServices: CompanyServices

    @MockBean
    lateinit var userServices: UserServices

    @MockBean
    lateinit var auth: AuthenticationsInterceptor

    private val mapper = jacksonObjectMapper()

      @Test
      fun getExistentCompanyWithoutAuth(){
          //given
          val company = CompanyOutputDto(1,"123454081","Rua Actriz Palmira Bastos,Porto,Portugal","123456789","BEAUTY","Cabelos lindos e loiros e impecaveis","cablo",null,null,null,null)
          //when
          `when`(companyServices.getCompanyById(1)).thenReturn(company)
          val result: ResultActions = mockMvc.perform(MockMvcRequestBuilders.get("/company/1"))
          //then
          result.andExpect(MockMvcResultMatchers.status().`is`(200))
      }
}



