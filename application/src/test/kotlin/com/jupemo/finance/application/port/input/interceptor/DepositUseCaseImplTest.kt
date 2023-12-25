package com.jupemo.finance.application.port.input.interceptor

import com.jupemo.finance.application.exception.UserNotFoundException
import com.jupemo.finance.application.port.input.DepositUseCase
import com.jupemo.finance.application.port.output.UserGetByIdPort
import com.jupemo.finance.application.port.output.UserUpdatePort
import com.jupemo.finance.entity.User
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class DepositUseCaseImplTest {

    @InjectMockKs
    lateinit var depositUseCaseImpl: DepositUseCaseImpl

    @MockK
    lateinit var userGetByIdPort: UserGetByIdPort

    @MockK(relaxed = true)
    lateinit var userUpdatePort: UserUpdatePort

    @MockK(relaxed = true)
    lateinit var user: User

    @Test
    fun `deposit should update user`() {
        // given
        val command = DepositUseCase.Command(
            "100",
            "bankAccountId",
            "userId"
        )
        every { userGetByIdPort.getUserById(any()) } returns user
        // when
        depositUseCaseImpl.deposit(command)
        // then
        verify { userUpdatePort.updateUser(any()) }
    }

    @Test
    fun `deposit should return NotFoundException when user not found`() {
        // given
        val command = DepositUseCase.Command(
            "100",
            "bankAccountId",
            "userId"
        )
        every { userGetByIdPort.getUserById(any()) } returns null
        // when
        val exception = assertThrows(UserNotFoundException::class.java) {
            depositUseCaseImpl.deposit(command)
        }
        // then
        assertEquals("User with id: ${command.userId} found", exception.message)
    }
}