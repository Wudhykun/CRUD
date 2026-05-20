package com.user.crud

import com.user.crud.seed.SeedProperties
import io.github.cdimascio.dotenv.dotenv
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityScheme
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
@SecurityScheme(
	name = "bearerAuth",          // this name is referenced by @SecurityRequirement below
	type = SecuritySchemeType.HTTP,
	scheme = "bearer",
	bearerFormat = "JWT"
)

class Application

fun main(args: Array<String>) {
	val dotenv = dotenv { ignoreIfMissing = true }
	dotenv.entries().forEach { System.setProperty(it.key, it.value) }
	runApplication<Application>(*args)
}