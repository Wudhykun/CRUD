package com.user.crud

import com.user.crud.seed.SeedProperties
import io.github.cdimascio.dotenv.dotenv
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(SeedProperties::class)

class Application

fun main(args: Array<String>) {
	val dotenv = dotenv { ignoreIfMissing = true }
	dotenv.entries().forEach { System.setProperty(it.key, it.value) }
	runApplication<Application>(*args)
}