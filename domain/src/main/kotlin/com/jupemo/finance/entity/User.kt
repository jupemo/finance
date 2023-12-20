package com.jupemo.finance.entity

import java.math.BigDecimal

class User(
    private val name: String,
    private val email: String,
    private var id: String? = null
) {

    private var bankAccounts: List<BankAccount> = emptyList()

    fun createBankAccount(
        id: String,
        name: String,
        currency: String,
        type: BankAccountType,
        bank: String
    ) : BankAccount {
        val bankAccount = BankAccount(id, name, currency, type, bank)
        bankAccounts = bankAccounts.plus(bankAccount)
        return bankAccount
    }

    fun totalBalance(): BigDecimal {
        val totalBalance = BigDecimal.ZERO
        bankAccounts.forEach {
            totalBalance.plus(it.balance())
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
        if (from.balance() < amount) throw Exception("Insufficient funds")
        if (from.type() != BankAccountType.CHECKING) throw Exception("Cannot transfer from credit account")

        from.transferMoney(amount, to)
    }

    fun bankAccounts(): List<BankAccount> {
        return this.bankAccounts
    }

    fun name(): String {
        return this.name
    }

    fun email(): String {
        return this.email
    }

    fun id(id: String) {
        this.id = id
    }

    fun id(): String? {
        return this.id
    }

    fun bankAccounts(bankAccounts: List<BankAccount>) {
        this.bankAccounts = bankAccounts
    }

}