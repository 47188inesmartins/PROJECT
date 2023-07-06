import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.0.4"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
}

group = "ps.schedule"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {



	implementation("org.jdbi:jdbi3-core:3.33.0")
	implementation("org.jdbi:jdbi3-kotlin:3.33.0")
	implementation("org.jdbi:jdbi3-postgres:3.33.0")
	implementation("org.postgresql:postgresql:42.5.0")


	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springframework.security:spring-security-core:5.7.3")
	implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-serialization-json", version = "1.5.+")
	implementation ("net.bytebuddy:byte-buddy:1.11.10")


	// for JDBI
	implementation ("org.springframework:spring-jdbc:6.0.6")
	implementation("org.jdbi:jdbi3-core:3.33.0")
	implementation("org.jdbi:jdbi3-kotlin:3.33.0")
	implementation("org.jdbi:jdbi3-postgres:3.33.0")
	implementation("org.postgresql:postgresql:42.5.0")
	implementation("javax.persistence:javax.persistence-api:2.2")
	implementation ("org.springframework.boot:spring-boot-starter-validation")
	testImplementation(kotlin("test"))
	testImplementation ("com.h2database:h2:2.1.214")

	// for Geocoder
	implementation ("com.google.maps:google-maps-services:2.1.2")


	// JWT
	implementation("io.jsonwebtoken:jjwt:0.9.1")
	implementation("javax.servlet:javax.servlet-api:4.0.1")
	implementation("javax.servlet:jstl:1.2")
	implementation ("com.auth0:java-jwt:4.2.1")

	// for sending emails
	implementation ("org.springframework.boot:spring-boot-starter-mail")

	// Para processamento de arquivos multipart
	implementation  ("org.springframework.boot:spring-boot-starter-validation")
	implementation  ("commons-fileupload:commons-fileupload:1.4")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.junit.jupiter:junit-jupiter-engine")
	testImplementation("org.junit.jupiter:junit-jupiter-api")
	testImplementation("com.ninja-squad:springmockk:3.1.1")
}


tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
