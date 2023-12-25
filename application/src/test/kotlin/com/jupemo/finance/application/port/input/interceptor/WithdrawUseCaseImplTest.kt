package com.jupemo.finance.application.port.input.interceptor

import com.jupemo.finance.application.exception.NotFoundException
import com.jupemo.finance.application.port.input.WithdrawUseCase
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
class WithdrawUseCaseImplTest {

    @InjectMockKs
    lateinit var withdrawUseCaseImpl: WithdrawUseCaseImpl

    @MockK
    lateinit var userGetByIdPort: UserGetByIdPort

    @MockK(relaxed = true)
    lateinit var userUpdatePort: UserUpdatePort

    @MockK
    lateinit var user: User


    @Test
    fun `withdraw should call userGetByIdPort and userUpdatePort`() {
        // given
        val command = WithdrawUseCase.Command(userId = "1", amount = "100", bankAccountId = "1")
        every { userGetByIdPort.getUserById(command.userId) } returns user
        every { user.withdrawMoney(any(), any()) } returns Unit

        // when
        withdrawUseCaseImpl.withdraw(command)

        // then
        verify { userGetByIdPort.getUserById(command.userId) }
        verify { user.withdrawMoney(any(), any()) }
        verify { userUpdatePort.updateUser(user) }
    }

    @Test
    fun `withdraw should throw NotFoundException when userGetByIdPort returns null`() {
        // given
        val command = WithdrawUseCase.Command(userId = "1", amount = "100", bankAccountId = "1")
        every { userGetByIdPort.getUserById(command.userId) } returns null

        // when
        val error = assertThrows(NotFoundException::class.java) { withdrawUseCaseImpl.withdraw(command) }
        // then
        assertEquals(NotFoundException::class, error::class)
        verify { userGetByIdPort.getUserById(command.userId) }
        verify(exactly = 0) { user.withdrawMoney(any(), any()) }
        verify(exactly = 0) { userUpdatePort.updateUser(user) }
    }

    @Test
    fun `withdraw should throw IllegalArgumentException when amount is not a valid number`() {
        // when
        val error = assertThrows(IllegalArgumentException::class.java) {
            WithdrawUseCase.Command(
                userId = "1",
                amount = "abc",
                bankAccountId = "1"
            )
        }

        // then
        assertEquals(IllegalArgumentException::class, error::class)
        verify(exactly = 0) { userGetByIdPort.getUserById(any()) }
        verify(exactly = 0) { user.withdrawMoney(any(), any()) }
        verify(exactly = 0) { userUpdatePort.updateUser(user) }
    }

    @Test
    fun `withdraw should throw IllegalArgumentException when amount is less than zero`() {
        // when
        val error = assertThrows(IllegalArgumentException::class.java) {
            WithdrawUseCase.Command(
                userId = "1",
                amount = "-1",
                bankAccountId = "1"
            )
        }

        // then
        assertEquals(IllegalArgumentException::class, error::class)
        verify(exactly = 0) { userGetByIdPort.getUserById(any()) }
        verify(exactly = 0) { user.withdrawMoney(any(), any()) }
        verify(exactly = 0) { userUpdatePort.updateUser(user) }
    }
}