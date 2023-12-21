package com.jupemo.finance.application.port.input.interceptor

import com.jupemo.finance.application.exception.NotFoundException
import com.jupemo.finance.application.port.output.UserGetByIdPort
import com.jupemo.finance.entity.User
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class GetUserByIdUseCaseImplTest {

    @InjectMockKs
    lateinit var getUserByIdUseCaseImpl: GetUserByIdUseCaseImpl

    @MockK
    lateinit var getUserGetByIdPort: UserGetByIdPort

    @MockK
    lateinit var user: User

    @Test
    fun `getUserById should return a User with the given id`() {
        every { getUserGetByIdPort.getUserById(any()) } returns user
        val result = getUserByIdUseCaseImpl.getUserById("id")
        assertNotNull(result)
    }

    @Test
    fun `getUserById should return not found exception when user is not found`() {
        every { getUserGetByIdPort.getUserById(any()) } returns null

        val result = assertThrows(NotFoundException::class.java) {
            getUserByIdUseCaseImpl.getUserById("id")
        }

        assertEquals("User with id 'id'not found", result.message)

    }

}