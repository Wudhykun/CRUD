package com.user.crud

import com.user.crud.seed.SeedProperties
import io.github.cdimascio.dotenv.dotenv
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(SeedProperties::class)
@OpenAPIDefinition(
	info = Info(
		title = "User CRUD API",
		version = "1.0",
		description = "User management API with JWT authentication"
	)
)
class Application

fun main(args: Array<String>) {
	val dotenv = dotenv { ignoreIfMissing = true }
	dotenv.entries().forEach { System.setProperty(it.key, it.value) }
	runApplication<Application>(*args)
}