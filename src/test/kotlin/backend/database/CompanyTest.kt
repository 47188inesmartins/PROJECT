package backend.database

/*
@RunWith(SpringRunner::class)
@DataJpaTest
@AutoConfigureTestDatabase
class CompanyRepositoryTest {

    @Autowired
    private lateinit var companyRepository: CompanyRepository

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Test
    fun `Insert one company`() {
        val com = Company(
            nif = "250407671",
            address = "Rua Ferreira da Costa",
            compName = "Maf,Lda",
            compType = "Cabeleireiro",
            description = "Cabeleireira ",
            listOf(),
            null,
            listOf()
        )

        val a = entityManager.persist(com)
        entityManager.flush()

        val c = companyRepository.findAllById(a.id!!)

        assertEquals(a.id,c.id)
    }
}*/