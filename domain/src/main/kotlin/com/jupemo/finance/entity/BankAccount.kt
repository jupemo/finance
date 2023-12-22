package com.jupemo.finance.entity

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
        this.balance = this.balance.plus(amount)
    }

    internal fun withdrawMoney(amount: BigDecimal) {
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