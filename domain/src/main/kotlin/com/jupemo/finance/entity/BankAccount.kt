package com.jupemo.finance.entity

import java.math.BigDecimal

open class BankAccount (
    private val name : String,
    private val currency: String,
    private val type: BankAccountType,
    private val bank: String
){

    private var balance : BigDecimal = BigDecimal.ZERO


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

    internal fun getBalance () : BigDecimal {
        return this.balance
    }

    internal fun getType(): BankAccountType {
        return this.type
    }


}