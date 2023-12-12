package com.jupemo.finance.application.port.input.interceptor

import com.jupemo.finance.application.port.input.CreateUserUseCase
import com.jupemo.finance.application.port.output.TestMockClass
import com.jupemo.finance.application.port.output.UserSavePort
import com.jupemo.finance.entity.User
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.micronaut.test.annotation.MockBean
import io.mockk.every
import io.mockk.mockk
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@MicronautTest
class CreateUserUseCaseImplTest {

    @Inject
    lateinit var createUserUseCase: CreateUserUseCaseImpl

    @Inject
    lateinit var userSavePort: UserSavePort

    @MockBean(UserSavePort::class)
    fun userSavePort(): UserSavePort {
        return mockk(relaxed = true)
    }
    @MockBean(TestMockClass::class)
    fun testMockClass(): TestMockClass {
        return mockk()
    }

    @Test
    fun `createUser should return a User with the given name and email`() {
        every { userSavePort.saveUser(any()) } returns User("Mock User", "email@email.com")

        val result = createUserUseCase.createUser(CreateUserUseCase.Command("Mock User", "email@email.com"))
        assertEquals("Mock User", result.getName())
    }

}