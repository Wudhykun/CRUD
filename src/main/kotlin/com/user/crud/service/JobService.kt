package com.user.crud.service

import com.user.crud.dto.request.CreateJobRequest
import com.user.crud.dto.response.JobResponse
import com.user.crud.exception.UserNotFoundException
import com.user.crud.model.Job
import com.user.crud.repository.JobRepository
import com.user.crud.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class JobService(
    private var jobRepository: JobRepository,
    private var userRepository: UserRepository,
    ) {
    fun createJob(userId: Long, request: CreateJobRequest): JobResponse {
        val user = userRepository.findById(userId)
            .orElseThrow { UserNotFoundException("User with id $userId not found.") }

        val job = Job(
            title = request.title,
            user = user
        )

        return jobRepository.save(job).toResponse()
    }


    fun getAllJobs(): List<JobResponse> {
        return jobRepository.findAll().map { it.toResponse() }
    }


    fun getJobsByUserId(userId: Long): List<JobResponse> {
            userRepository.findById(userId)
            .orElseThrow{ UserNotFoundException("User with id $userId not found.") }

        return jobRepository.findByUserId(userId).map { it.toResponse() }
    }


    fun deleteJob(jobId: Long) {
        val job = jobRepository.findById(jobId)
            .orElseThrow { UserNotFoundException("Job with id $jobId not found.") }

        jobRepository.delete(job)
    }

    private fun Job.toResponse(): JobResponse {
        return JobResponse(
            id = id,
            title = title,
            userId = user.id,
        )
    }
}