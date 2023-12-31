package com.jupemo.finance.entity

import com.jupemo.finance.exception.InsufficientFundsException
import com.jupemo.finance.exception.NegativeNumberException
import java.math.BigDecimal

class BankAccount(
    private val id: String,
    private val name: String,
    private val currency: String,
    private val type: BankAccountType,
    private val bank: String,
    private var balance: BigDecimal = BigDecimal.ZERO
) {


    internal fun transferMoney(amount: BigDecimal, to: BankAccount) {
        withdrawMoney(amount)
        depositMoney(amount)
    }

    internal fun depositMoney(amount: BigDecimal) {
        if (amount < BigDecimal.ZERO) throw NegativeNumberException()
        this.balance = this.balance.plus(amount)
    }

    internal fun withdrawMoney(amount: BigDecimal) {
        if (amount < BigDecimal.ZERO) throw NegativeNumberException()
        if (this.balance.minus(amount) < BigDecimal.ZERO) throw InsufficientFundsException()
        this.balance = this.balance.minus(amount)
    }

    fun balance(): BigDecimal {
        return this.balance
    }

    fun type(): BankAccountType {
        return this.type
    }

    fun name(): String {
        return this.name
    }

    fun currency(): String {
        return this.currency
    }

    fun bank(): String {
        return this.bank
    }

    fun id(): String {
        return this.id
    }


}