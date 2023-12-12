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

    fun balance () : BigDecimal {
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




}