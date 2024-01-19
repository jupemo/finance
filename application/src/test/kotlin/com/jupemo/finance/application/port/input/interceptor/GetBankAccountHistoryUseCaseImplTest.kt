package com.jupemo.finance.application.port.input.interceptor

import com.jupemo.finance.application.port.input.GetBankAccountHistoryUseCase
import com.jupemo.finance.application.port.output.UserGetByIdPort
import com.jupemo.finance.entity.*
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
class GetBankAccountHistoryUseCaseImplTest {

    @InjectMockKs
    private lateinit var getBankAccountHistoryUseCaseImpl: GetBankAccountHistoryUseCaseImpl

    @MockK
    private lateinit var userGetByIdPort: UserGetByIdPort

    @Test
    fun `should return history when history exists`() {
        val userId = "userId"
        val bankAccountId = "bankAccountId"
        val history = historyChanges()
        val bankAccount = bankAccount(history)
        val user = user()
        user.bankAccounts(listOf(bankAccount))

        every { userGetByIdPort.getUserById(userId) } returns user

        val command = GetBankAccountHistoryUseCase.Command(userId, bankAccountId, 0, 2)
        val result = getBankAccountHistoryUseCaseImpl.getBankAccountHistory(command)

        assertEquals(history, result)
    }


    @Test
    fun `should return empty list when history does not exist`() {
        val userId = "userId"
        val bankAccountId = "bankAccountId"
        val user = user()

        every { userGetByIdPort.getUserById(userId) } returns user

        val command = GetBankAccountHistoryUseCase.Command(userId, bankAccountId, 0, 2)
        val result = getBankAccountHistoryUseCaseImpl.getBankAccountHistory(command)

        assertTrue(result.isEmpty())
    }


    @Test
    fun `should return empty list when page is out of range`() {
        val userId = "userId"
        val bankAccountId = "bankAccountId"
        val history = historyChanges()
        val bankAccount = bankAccount(history)
        val user = user()
        user.bankAccounts(listOf(bankAccount))

        every { userGetByIdPort.getUserById(userId) } returns user

        val command = GetBankAccountHistoryUseCase.Command(userId, bankAccountId, 2, 2)
        val result = getBankAccountHistoryUseCaseImpl.getBankAccountHistory(command)

        assertTrue(result.isEmpty())
    }

    @Test
    fun `should return sublist when page is in range`() {
        val userId = "userId"
        val bankAccountId = "bankAccountId"
        val history = historyChanges()
        val bankAccount = bankAccount(history)
        val user = user()
        user.bankAccounts(listOf(bankAccount))

        every { userGetByIdPort.getUserById(userId) } returns user

        val command = GetBankAccountHistoryUseCase.Command(userId, bankAccountId, 1, 1)
        val result = getBankAccountHistoryUseCaseImpl.getBankAccountHistory(command)

        assertEquals(history[1], result[0])
    }

    private fun bankAccount(
        history: List<HistoryChange>
    ): BankAccount {
        val bankAccount = BankAccount(
            "bankAccountId",
            name = "bankAccountName",
            currency = "currency",
            type = BankAccountType.CHECKING,
            bank = "bank",
            balance = BigDecimal.valueOf(2),
            history = history
        )
        return bankAccount
    }
    private fun user(): User {
        val user = User(
            id = "userId",
            name = "name",
            email = "email",
        )
        return user
    }

    private fun historyChanges(): List<HistoryChange> {
        val history = listOf(
            HistoryChange(
                amount = BigDecimal.valueOf(2),
                action = ActionType.DEPOSIT,
                date = LocalDateTime.now()
            ),
            HistoryChange(
                amount = BigDecimal.valueOf(2),
                action = ActionType.DEPOSIT,
                date = LocalDateTime.now()
            )
        )
        return history
    }
}
