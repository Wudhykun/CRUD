package com.user.crud.controller

import com.user.crud.dto.request.CreateJobRequest
import com.user.crud.service.JobService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
@Tag(name = "Jobs", description = "Job management endpoints")
class JobController(private val jobService: JobService) {

    @PostMapping("/users/{userId}/jobs")
    @Operation(summary = "Create job", description = "Create a new job  for user.")
    fun createJob(
        @PathVariable userId: Long,
        @RequestBody request: CreateJobRequest
    ) = jobService.createJob(userId, request)

    @GetMapping("/jobs")
    @Operation(summary = "List all jobs", description = "Returns all jobs in the system.")
    fun getAllJobs() = jobService.getAllJobs()

    @GetMapping("/users/{userId}/jobs")
    @Operation(summary = "Get jobs by user",  description = "Returns all jobs belonging to a specific user.")
    fun getJobByUserId(
        @PathVariable userId: Long
    ) = jobService.getJobsByUserId(userId)

    @DeleteMapping("/jobs/{jobId}")
    @Operation(summary = "Delete job", description = "Permanently deletes a job by ID.")
    fun deleteJob(
        @PathVariable jobId: Long
    ) = jobService.deleteJob(jobId)
}