package com.jupemo.finance.application.port.input.interceptor

import com.jupemo.finance.application.port.input.CreateUserUseCase
import com.jupemo.finance.application.port.output.UserSavePort
import com.jupemo.finance.entity.User
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class CreateUserUseCaseImplTest {

    @InjectMockKs
    lateinit var createUserUseCase: CreateUserUseCaseImpl

    @MockK
    lateinit var userSavePort: UserSavePort

    @Test
    fun `createUser should return a User with the given name and email`() {
        every { userSavePort.saveUser(any()) } returns User("Mock User", "email@email.com")

        val result = createUserUseCase.createUser(CreateUserUseCase.Command("Mock User", "email@email.com"))
        assertEquals("Mock User", result.name())
    }

}