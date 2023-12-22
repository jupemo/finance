package com.jupemo.finance.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class UserTest {

    @Test
    fun `should create user`() {
        // given
        val name = "name"
        val email = "email"
        // when
        val user = User(name, email)
        // then
        assertEquals(name, user.name())
        assertEquals(email, user.email())
    }

    @Test
    fun `should create bankAccount`() {
        // given
        val name = "name"
        val email = "email"
        val user = User(name, email)
        val id = "id"
        val bankAccountName = "bankAccountName"
        val currency = "currency"
        val type = BankAccountType.CHECKING
        val bank = "bank"
        // when
        val bankAccount = user.createBankAccount(id, bankAccountName, currency, type, bank)
        // then
        assertEquals(id, bankAccount.id())
        assertEquals(bankAccountName, bankAccount.name())
        assertEquals(currency, bankAccount.currency())
        assertEquals(type, bankAccount.type())
        assertEquals(bank, bankAccount.bank())
    }

    @Test
    fun `should deposit in bankAccount`() {
        // given
        val name = "name"
        val email = "email"
        val user = User(name, email)
        val id = "id"
        val bankAccountName = "bankAccountName"
        val currency = "currency"
        val type = BankAccountType.CHECKING
        val bank = "bank"
        val bankAccount = user.createBankAccount(id, bankAccountName, currency, type, bank)
        // when
        user.depositMoney(BigDecimal("100"), bankAccount.id())
        // then
        assertEquals(BigDecimal("100"), bankAccount.balance())
    }

    fun `should withraw in bankAccount`() {
        // given
        val name = "name"
        val email = "email"
        val user = User(name, email)
        val id = "id"
        val bankAccountName = "bankAccountName"
        val currency = "currency"
        val type = BankAccountType.CHECKING
        val bank = "bank"
        val bankAccount = user.createBankAccount(id, bankAccountName, currency, type, bank)
        // when
        user.depositMoney(BigDecimal("100"), bankAccount.id())
        user.withdrawMoney(BigDecimal("50"), bankAccount.id())
        // then
        assertEquals(BigDecimal("50"), bankAccount.balance())
    }
}