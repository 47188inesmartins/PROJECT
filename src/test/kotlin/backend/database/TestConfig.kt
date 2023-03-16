package backend.database

import jakarta.activation.DataSource
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.datasource.DriverManagerDataSource

@TestConfiguration
class TestConfig(
    @Value("\${spring.test-datasource.url}")
    val dbUrl: String,

    @Value("\${spring.test-datasource.username}")
    val dbUsername: String,

    @Value("\${spring.test-datasource.password}")
    val dbPassword: String
) {
    @Bean
    fun dataSource(): DriverManagerDataSource = DriverManagerDataSource()
        .also {
            it.setDriverClassName("org.postgresql.Driver")
            it.url = dbUrl
            it.username = dbUsername
            it.password = dbPassword
        }
}