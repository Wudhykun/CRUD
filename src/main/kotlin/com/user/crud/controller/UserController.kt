package com.user.crud.controller

import com.user.crud.dto.request.AddUserRequest
import com.user.crud.dto.request.UpdateUserRequest
import com.user.crud.service.UserService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(val userService: UserService) {
    @PostMapping
    fun addUser(@RequestBody request: AddUserRequest) = userService.addUser(request)

    // TODO("update user")
    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody request: UpdateUserRequest) = userService.updateUser(id, request)

    // TODO("list all users")
    @GetMapping
    fun listAllUsers() = userService.listAllUsers()

    // TODO("delete user")
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long)= userService.deleteUser(id)
}