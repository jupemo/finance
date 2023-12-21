package com.jupemo.finance.application.port.input.interceptor

import com.jupemo.finance.application.exception.NotFoundException
import com.jupemo.finance.application.port.input.CreateBankAccountUseCase
import com.jupemo.finance.application.port.output.UserGetByIdPort
import com.jupemo.finance.application.port.output.UserUpdatePort
import com.jupemo.finance.entity.User
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class CreateBankAccountUseCaseImplTest {

    @InjectMockKs
    lateinit var createBankAccountUseCase: CreateBankAccountUseCaseImpl

    @MockK
    lateinit var userGetPort: UserGetByIdPort

    @MockK(relaxed = true)
    lateinit var userUpdatePort: UserUpdatePort

    @Test
    fun `createBankAccount should return a CreateBankAccountUseCase Return`() {
        val captureUser = slot<User>()

        //Given
        val command = CreateBankAccountUseCase.Command(
            userId = "1",
            name = "Mock Bank Account",
            currency = "BRL",
            type = "CHECKING",
            bank = "Mock Bank"
        )

        val user = User(
            id = "1",
            name = "Mock User",
            email = "email"
        )

        every { userGetPort.getUserById(any()) } returns user
        every { userUpdatePort.updateUser(capture(captureUser)) } returns user

        //When
        val result = createBankAccountUseCase.createBankAccount(command)

        //Then
        verify { userGetPort.getUserById("1") }
        verify { userUpdatePort.updateUser(any()) }

        assertAll(
            {
                assertEquals(command.name, result.name)
                assertEquals(command.bank, result.bank)
                assertEquals(command.type, result.type)
                assertNotNull(result.id)
            },
            {
                assertEquals(1, captureUser.captured.bankAccounts().size)
                assertEquals(command.name, captureUser.captured.bankAccounts()[0].name())
                assertEquals(command.bank, captureUser.captured.bankAccounts()[0].bank())
                assertEquals(command.type, captureUser.captured.bankAccounts()[0].type().toString())
                assertNotNull(captureUser.captured.bankAccounts()[0].id())
            }
        )
    }

    @Test
    fun `user not found on create bank account`() {
        // Given
        val command = CreateBankAccountUseCase.Command(
            userId = "1",
            name = "Mock Bank Account",
            currency = "BRL",
            type = "CHECKING",
            bank = "Mock Bank"
        )

        every { userGetPort.getUserById(any()) } returns null

        // When
        val exception = assertThrows(NotFoundException::class.java) {
            createBankAccountUseCase.createBankAccount(command)
        }

        // Then
        assertEquals("User with id '1'not found", exception.message)
    }

    @Test
    fun `command not complete on create bank account`() {
        // When
        val exception = assertThrows(IllegalArgumentException::class.java) {
            CreateBankAccountUseCase.Command(
                userId = "1",
                name = "",
                currency = "",
                type = "",
                bank = ""
            )

        }

        // Then
        assertEquals("Name must not be blank", exception.message)
    }

}