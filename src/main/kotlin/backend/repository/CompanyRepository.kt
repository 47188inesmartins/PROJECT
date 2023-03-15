package backend.repository

import java.util.*

interface CompanyRepository {
    fun get(email: String): Boolean
    fun remove(email: String) : Boolean
    fun add(email: String, password: String, username: String, name: String, type: String, description: String) : Int?
}