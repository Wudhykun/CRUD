package com.user.crud.seed

import com.user.crud.model.Job
import com.user.crud.model.User
import com.user.crud.repository.JobRepository
import com.user.crud.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
@Profile("dev")
class DevDataSeeder(
    private val userRepository: UserRepository,
    private val jobRepository: JobRepository,
    private val seedProperties: SeedProperties,
    private val passwordEncoder: PasswordEncoder
) : CommandLineRunner {

    override fun run(vararg args: String) {
        if (!seedProperties.enabled) return
        if (userRepository.count() > 0) return

        val users = seedUsers(seedProperties.userCount)
        seedJobs(users, seedProperties.jobCount)
    }

    private fun seedUsers(count: Int): List<User> {
        val users = (1..count).map {
            User(
                username = "user$it",
                email = "user$it@example.com",
                password = passwordEncoder.encode("password123")!!
            )
        }
        return userRepository.saveAll(users)
    }

    private fun seedJobs(users: List<User>, count: Int) {
        val titles = listOf(
            "Backend Developer",
            "Frontend Developer",
            "QA Engineer",
            "DevOps Engineer",
            "Data Analyst"
        )

        val jobs = (1..count).map {
            Job(
                title = titles.random(),
                user = users.random()
            )
        }

        jobRepository.saveAll(jobs)
    }
}