package com.user.crud

import com.user.crud.dto.request.AddUserRequest
import com.user.crud.dto.request.UpdateUserRequest
import com.user.crud.exception.DuplicateEmailException
import com.user.crud.exception.DuplicateUsernameException
import com.user.crud.exception.UserNotFoundException
import com.user.crud.model.Role
import com.user.crud.model.User
import com.user.crud.repository.UserRepository
import com.user.crud.service.UserService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.Optional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class UserServiceTest {

    private val userRepository: UserRepository = mock()
    private val passwordEncoder: PasswordEncoder = mock()
    private val userService = UserService(userRepository, passwordEncoder)

    @Test
    fun `addUser - success`() {
        val request = AddUserRequest("john", "john@email.com", "Secret@123", Role.USER)
        whenever(userRepository.existsByUsername("john")).thenReturn(false)
        whenever(userRepository.existsByEmail("john@email.com")).thenReturn(false)
        whenever(passwordEncoder.encode("Secret@123")).thenReturn("hashed")
        val savedUser = User(1L, "john", "john@email.com", "hashed", Role.USER)
        whenever(userRepository.save(org.mockito.kotlin.any())).thenReturn(savedUser)

        val result = userService.addUser(request)

        assertEquals("john", result.username)
        assertEquals("john@email.com", result.email)
    }

    @Test
    fun `addUser - duplicate username throws exception`() {
        val request = AddUserRequest("john", "john@email.com", "Secret@123", Role.USER)
        whenever(userRepository.existsByUsername("john")).thenReturn(true)

        assertThrows<DuplicateUsernameException> { userService.addUser(request) }
    }

    @Test
    fun `addUser - duplicate email throws exception`() {
        val request = AddUserRequest("john", "john@email.com", "Secret@123", Role.USER)
        whenever(userRepository.existsByUsername("john")).thenReturn(false)
        whenever(userRepository.existsByEmail("john@email.com")).thenReturn(true)

        assertThrows<DuplicateEmailException> { userService.addUser(request) }
    }

    @Test
    fun `deleteUser - success`() {
        val user = User(1L, "john", "john@email.com", "hashed", Role.USER)
        whenever(userRepository.findById(1L)).thenReturn(Optional.of(user))

        val result = userService.deleteUser(1L)

        assertEquals("User with id 1 deleted successfully.", result.message)
    }

    @Test
    fun `deleteUser - user not found throws exception`() {
        whenever(userRepository.findById(99L)).thenReturn(Optional.empty())

        assertThrows<UserNotFoundException> { userService.deleteUser(99L) }
    }

    @Test
    fun `listAllUsers - returns mapped list`() {
        val users = listOf(
            User(1L, "john", "john@email.com", "hashed", Role.USER),
            User(2L, "jane", "jane@email.com", "hashed", Role.ADMIN)
        )
        whenever(userRepository.findAll()).thenReturn(users)

        val result = userService.listAllUsers()

        assertEquals(2, result.size)
        assertEquals("john", result[0].username)
    }

    @Test
    fun `updateUser - user not found throws exception`() {
        val request = UpdateUserRequest("newname", "new@email.com")
        whenever(userRepository.findById(99L)).thenReturn(Optional.empty())

        assertThrows<UserNotFoundException> { userService.updateUser(99L, request) }
    }
}