package com.jupemo.finance.entity

import java.math.BigDecimal

open class BankAccount (
    private val name : String,
    private val currency: String,
    private val type: BankAccountType,
    private val bank: String,
    private val balance: BigDecimal = BigDecimal.ZERO
){


    internal fun transferMoney (amount : BigDecimal, to : BankAccount) {
        withdrawMoney(amount)
        depositMoney(amount)
    }

    internal fun depositMoney (amount : BigDecimal) {
        this.balance.plus(amount)
    }

    internal fun withdrawMoney (amount : BigDecimal) {
        this.balance.minus(amount)
    }

    fun getBalance () : BigDecimal {
        return this.balance
    }

    fun getType(): BankAccountType {
        return this.type
    }

    fun getName(): String {
        return this.name
    }

    fun getCurrency(): String {
        return this.currency
    }

    fun getBank(): String {
        return this.bank
    }




}