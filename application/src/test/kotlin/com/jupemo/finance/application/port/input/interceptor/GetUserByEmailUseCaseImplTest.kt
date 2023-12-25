package com.jupemo.finance.application.port.input.interceptor

import com.jupemo.finance.application.exception.UserNotFoundException
import com.jupemo.finance.application.port.output.UserGetByEmailPort
import com.jupemo.finance.entity.User
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
class GetUserByEmailUseCaseImplTest {

    @InjectMockKs
    lateinit var getUserByEmailUseCase: GetUserByEmailUseCaseImpl

    @MockK
    lateinit var getUserPort: UserGetByEmailPort

    @Test
    fun `getUserByEmail should return a User with the given email`() {
        every { getUserPort.getUserByEmail(any()) } returns User(
            "Mock User",
            "email",
            id = UUID.randomUUID().toString()
        )

        val user = getUserByEmailUseCase.getUserByEmail("email")

        verify { getUserPort.getUserByEmail("email") }
        assertNotNull(user)
        assertEquals("Mock User", user.name)
        assertEquals("email", user.email)
    }

    @Test
    fun `getUserByEmail should throw Not Found Exception when user is not found`() {
        every { getUserPort.getUserByEmail(any()) } returns null

        val result = assertThrows(UserNotFoundException::class.java) {
            getUserByEmailUseCase.getUserByEmail("email@notfound.com")
        }

        assertEquals("User with email 'email@notfound.com' not found.", result.message)

    }
}