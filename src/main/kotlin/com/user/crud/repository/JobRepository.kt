package com.user.crud.repository

import com.user.crud.model.Job
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JobRepository : JpaRepository<Job, Long> {
    fun findByUserId(userId: Long): List<Job>
}