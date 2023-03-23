package backend.services

import backend.domain.Company
import org.springframework.stereotype.Component
import org.springframework.transaction.TransactionManager
import java.time.Clock
import java.util.*

/*
@Component
class CompanyServices(private val transactionManager: TransactionManager) {

    fun getCompany(email: String) : Company?{
        return transactionManager.run {
            //it.CompanyRepository.get(email)
        }
    }
}
*/