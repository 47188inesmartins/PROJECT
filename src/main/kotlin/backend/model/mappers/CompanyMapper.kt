package backend.model.mappers

import backend.domain.Company
import javax.persistence.EntityManager

class CompanyMapper : IMapperModel<Company, Int> {

    private lateinit var entityManager: EntityManager

    override fun add(entity: Company): Int? {
        entityManager.persist(entity)
        return entity.id
    }
    override fun remove(key: Int): Boolean {
        val company = get(key)
        entityManager.remove(company)
        return true
    }

    override fun get(key: Int): Company = entityManager.find(Company::class.java, key)
}