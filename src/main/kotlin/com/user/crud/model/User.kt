package com.user.crud.model

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val username: String,
    val email: String,
    val password: String,

    @Enumerated(EnumType.STRING)
    val role: Role,

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    val jobs: List<Job> = emptyList()
)