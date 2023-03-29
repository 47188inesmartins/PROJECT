package backend.jvm.http

import com.fasterxml.jackson.databind.annotation.JsonSerialize


@JsonSerialize
data class CompanyResquest (
                    val nif:Int,
                    val address:String,
                    val name:String,
                    val type:String,
                    val description: String
                )

@JsonSerialize
data class CompanyResponse (
    val nif:String,
    val address:String,
    val name:String,
    val type:String,
    val description: String
)