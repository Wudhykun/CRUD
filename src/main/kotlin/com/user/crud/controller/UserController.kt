package com.user.crud.controller

import com.user.crud.dto.request.UpdateUserRequest
import com.user.crud.service.UserService
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
class UserController(val userService: UserService) {

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody @Valid request: UpdateUserRequest) = userService.updateUser(id, request)

    @GetMapping
    fun listAllUsers() = userService.listAllUsers()

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long)= userService.deleteUser(id)
}