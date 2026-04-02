package com.user.crud.controller

import com.user.crud.dto.request.CreateJobRequest
import com.user.crud.service.JobService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class JobController(private val jobService: JobService) {

    @PostMapping("/users/{userId}/jobs")
    fun createJob(
        @PathVariable userId: Long,
        @RequestBody request: CreateJobRequest
    ) = jobService.createJob(userId, request)

    @GetMapping("/jobs")
    fun getAllJobs() = jobService.getAllJobs()

    @GetMapping("/users/{userId}/jobs")
    fun getJobByUserId(
        @PathVariable userId: Long
    ) = jobService.getJobsByUserId(userId)

    @DeleteMapping("/jobs/{jobId}")
    fun deleteJob(
        @PathVariable jobId: Long
    ) = jobService.deleteJob(jobId)
}