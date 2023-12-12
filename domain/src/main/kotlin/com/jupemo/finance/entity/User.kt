package com.jupemo.finance.entity

import java.math.BigDecimal

class User(
    private val name: String,
    private val email: String
) {

    private var id: String? = null
    private var bankAccounts: List<BankAccount> = emptyList()

    fun createBankAccount(
        name: String,
        currency: String,
        type: BankAccountType,
        bank: String
    ) {
        val bankAccount = BankAccount(name, currency, type, bank)
        bankAccounts = bankAccounts.plus(bankAccount)
        println("Bank account created")
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

    fun getBankAccounts(): List<BankAccount> {
        return this.bankAccounts
    }

    fun getName(): String {
        return this.name
    }

    fun getEmail(): String {
        return this.email
    }

    fun setId(id: String) {
        this.id = id
    }

    fun getId(): String? {
        return this.id
    }

    fun setBankAccounts(bankAccounts: List<BankAccount>) {
        this.bankAccounts = bankAccounts
    }

}