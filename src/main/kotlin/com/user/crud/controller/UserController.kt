package com.user.crud.controller

import com.user.crud.dto.request.UpdateUserRequest
import com.user.crud.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
@Tag(name = "User", description ="User management endpoints")
class UserController(val userService: UserService) {

    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Updates username, email, or role for the given user ID.")
    fun updateUser(@PathVariable id: Long, @RequestBody @Valid request: UpdateUserRequest) = userService.updateUser(id, request)

    @GetMapping
    @Operation(summary = "List all users", description = "Returns all registered users")
    fun listAllUsers() = userService.listAllUsers()

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Permanently deletes a user by ID.")
    fun deleteUser(@PathVariable id: Long)= userService.deleteUser(id)
}