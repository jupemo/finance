package com.jupemo.finance.entity

import java.math.BigDecimal

class User(
    private val name: String,
    private val email: String
) {

    private lateinit var bankAccounts: List<BankAccount>

    init {
        bankAccounts.plus(BankAccount("Checking account", "EUR", BankAccountType.CHECKING, "European Bank"))
    }

    fun createBankAccount(
        name: String,
        currency: String,
        type: BankAccountType,
        bank: String
    ) {
        val bankAccount = BankAccount(name, currency, type, bank)
        bankAccounts.plus(bankAccount)
    }

    fun getTotalBalance(): BigDecimal {
        val totalBalance = BigDecimal.ZERO
        bankAccounts.forEach {
            totalBalance.plus(it.getBalance())
        }
        return totalBalance
    }

    fun depositMoney(amount: BigDecimal, bankAccount: BankAccount) {
        bankAccount.depositMoney(amount)
    }

    fun withdrawMoney(amount: BigDecimal, bankAccount: BankAccount) {
        bankAccount.withdrawMoney(amount)
    }

    fun transferMoney(amount: BigDecimal, from: BankAccount, to: BankAccount) {
        if (from.getBalance() < amount) throw Exception("Insufficient funds")
        if (from.getType() != BankAccountType.CHECKING) throw Exception("Cannot transfer from credit account")

        from.transferMoney(amount, to)
    }

}