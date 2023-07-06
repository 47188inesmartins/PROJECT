package backend.database

/*

@RunWith(SpringRunner::class)
@DataJpaTest
@AutoConfigureTestDatabase
class UsersTest {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var roleRepository: RoleRepository

    @Autowired
    private lateinit var entityManager: TestEntityManager


    @Test
    fun `get user`(){
        val userDB = UserDB(
            "test@123.com",
            "123456",
            "test",
            Date.valueOf("2000-01-01"),
            listOf(),
            listOf()
        )

        val role = Role("user",userDB)

        val userInsert = entityManager.persist(userDB)
        val roleInsert = entityManager.persist(role)
        entityManager.flush()

        println(userInsert.id)
        val get = userRepository.getRole(userInsert.id!!)

        assertEquals("user",get)
    }

}*/