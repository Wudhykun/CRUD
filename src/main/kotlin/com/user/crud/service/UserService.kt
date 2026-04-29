package com.user.crud.service

import com.user.crud.dto.request.AddUserRequest
import com.user.crud.dto.request.UpdateUserRequest
import com.user.crud.dto.response.AddUserResponse
import com.user.crud.dto.response.DeleteUserResponse
import com.user.crud.dto.response.ListUserResponse
import com.user.crud.exception.DuplicateEmailException
import com.user.crud.exception.DuplicateUsernameException
import com.user.crud.exception.UserNotFoundException
import com.user.crud.model.User
import com.user.crud.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(var userRepository: UserRepository) {

    // TODO("Register new user task")
    // 1. new Request Payload to enable of registering user with role as `ADMIN` or `USER`
    // 2. use BCryptPasswordEncoder to hash password and save to db
    fun addUser(request: AddUserRequest): AddUserResponse {
        // TODO("Business rule: no duplicate username")
        if (userRepository.existsByUsername(request.username)) {
            throw DuplicateUsernameException("Username \"${request.username}\" already exist.")
        }
        // TODO("Business rule: no duplicate emails")
        if (userRepository.existsByEmail(request.email))  {
            throw DuplicateEmailException("Email \"${request.email}\" already exist.")
        }

        val user = User(
            username = request.username,
            email = request.email
        )

        val savedUser = userRepository.save(user)

        return AddUserResponse(
            id = savedUser.id,
            username = savedUser.username,
            email = savedUser.email
        )
    }
        // TODO("update user")
    fun updateUser(id: Long, request: UpdateUserRequest): AddUserResponse {
        val existingUser = userRepository.findById(id).orElseThrow { UserNotFoundException("User with id $id not found.") }

        if (existingUser.username != request.username && userRepository.existsByUsername(request.username)) {
            throw DuplicateUsernameException("Username \"${request.username}\" already exists.")
        }

        if (existingUser.email != request.email && userRepository.existsByEmail(request.email)) {
            throw DuplicateEmailException("Email \"${request.email}\" already exist.")
        }

        val updatedUser = User(
            id = existingUser.id,
            username = request.username,
            email = request.email
        )

        val savedUser = userRepository.save(updatedUser)

        return AddUserResponse(
            id = savedUser.id,
            username= savedUser.username,
            email= savedUser.email
        )
    }

        // TODO("list all users")
    fun listAllUsers(): List<ListUserResponse> {
        val users = userRepository.findAll()

        return users.map { user -> ListUserResponse(
            id = user.id,
            username = user.username,
            email = user.email
        ) }
    }

    // TODO("delete user")
    fun deleteUser(id: Long): DeleteUserResponse {
        val existingUser = userRepository.findById(id).orElseThrow { UserNotFoundException("User with id $id not found.") }

        userRepository.delete(existingUser)

        return DeleteUserResponse(
            message = "User with id $id deleted successfully."
        )
    }
}