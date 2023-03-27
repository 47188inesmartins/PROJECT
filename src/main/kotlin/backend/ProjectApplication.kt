package backend

import org.jdbi.v3.core.Jdbi
import org.postgresql.ds.PGSimpleDataSource
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class ProjectApplication{
	@Bean
	fun jdbi() = Jdbi.create(
		PGSimpleDataSource().apply {
			setURL(System.getenv("DATABASE_PS"))
		}
	)
}

fun main(args: Array<String>) {
	runApplication<ProjectApplication>(*args)
}
