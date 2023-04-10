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
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("junit:junit:4.13.1")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springframework.security:spring-security-core:5.7.3")
	implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-serialization-json", version = "1.5.+")

	// for JDBI
	implementation ("org.springframework:spring-jdbc:6.0.6")
	implementation("org.jdbi:jdbi3-core:3.33.0")
	implementation("org.jdbi:jdbi3-kotlin:3.33.0")
	implementation("org.jdbi:jdbi3-postgres:3.33.0")
	implementation("org.postgresql:postgresql:42.5.0")
	implementation("javax.persistence:javax.persistence-api:2.2")
	implementation ("org.springframework.boot:spring-boot-starter-validation")
	testImplementation(kotlin("test"))
	testImplementation ("junit:junit:4.12")
	testImplementation ("com.h2database:h2:2.1.214")
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
