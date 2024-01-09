package com.jupemo.finance.entity

import com.jupemo.finance.exception.InsufficientFundsException
import com.jupemo.finance.exception.NegativeNumberException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal

class BankAccountTest {

    @Test
    fun `should throw exception when withdraw amount is greater than balance`() {
        val bankAccount = BankAccount(
            id = "1",
            balance = BigDecimal(100),
            type = BankAccountType.CHECKING,
            bank = "bank",
            currency = "USD",
            name = "name"
        )

        assertThrows<InsufficientFundsException> {
            bankAccount.withdrawMoney(BigDecimal(200))
        }
    }

    @Test
    fun `should throw exception when deposit amount is negative`() {
        val bankAccount = BankAccount(
            id = "1",
            balance = BigDecimal(100),
            type = BankAccountType.CHECKING,
            bank = "bank",
            currency = "USD",
            name = "name"
        )

        assertThrows<NegativeNumberException> {
            bankAccount.depositMoney(BigDecimal(-100))
        }
    }

    @Test
    fun `should throw exception when withdraw amount is negative`() {
        val bankAccount = BankAccount(
            id = "1",
            balance = BigDecimal(100),
            type = BankAccountType.CHECKING,
            bank = "bank",
            currency = "USD",
            name = "name"
        )

        assertThrows<NegativeNumberException> {
            bankAccount.withdrawMoney(BigDecimal(-100))
        }
    }

    @Test
    fun `should deposit amount when amount is positive`() {
        val bankAccount = BankAccount(
            id = "1",
            balance = BigDecimal(100),
            type = BankAccountType.CHECKING,
            bank = "bank",
            currency = "USD",
            name = "name"
        )
        bankAccount.depositMoney(BigDecimal(100))

        assertEquals(BigDecimal(200), bankAccount.balance())
        assertEquals(1, bankAccount.history().size)
        assertEquals(ActionType.DEPOSIT, bankAccount.history()[0].action())
        assertEquals(BigDecimal(100), bankAccount.history()[0].amount())
    }

    @Test
    fun `should withdraw amount when amount is positive and balance is greater than amount`() {
        val bankAccount = BankAccount(
            id = "1",
            balance = BigDecimal(100),
            type = BankAccountType.CHECKING,
            bank = "bank",
            currency = "USD",
            name = "name"
        )
        bankAccount.withdrawMoney(BigDecimal(50))

        assertEquals(BigDecimal(50), bankAccount.balance())
        assertEquals(1, bankAccount.history().size)
        assertEquals(ActionType.WITHDRAW, bankAccount.history()[0].action())
        assertEquals(BigDecimal(50), bankAccount.history()[0].amount())
    }

}