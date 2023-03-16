package backend.domain

data class Company (
        val email:String,
        val password:String,
        val compName:String,
        val compType: String,
        val description:String
)
