package backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties
class ProjectApplication

fun main(args: Array<String>) {
	runApplication<ProjectApplication>(*args)
}


