package com.user.crud.seed

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.seed")
data class SeedProperties(
    val enabled: Boolean = false,
    val userCount: Int = 5,
    val jobCount: Int = 10
)