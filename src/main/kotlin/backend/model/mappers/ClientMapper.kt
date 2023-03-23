package backend.model.mappers
import backend.domain.User
import javax.persistence.EntityManager

class ClientMapper:IMapperModel<User,Int>{

    private lateinit var entityManager: EntityManager

    override fun add(entity : User): Int? {
        entityManager.persist(entity)
        return entity.id
    }

    override fun remove(key : Int): Boolean{
        entityManager.remove(get(key))
        return true
    }

    override fun get(key: Int): User{
        return entityManager.find(User::class.java,key)
    }

    fun getUserByEmail(email:String):User{
        val stm = "select id,clientName,username from User where email=:email"
        val query = entityManager.createQuery(stm,User::class.java)
        query.setParameter("email",email)
        return query.singleResult
    }


}