package com.jupemo.finance.application.port.input

import java.math.BigDecimal

interface DepositUseCase {
    fun deposit(command: Command)

    data class Command(
        val amount: String, val bankAccountId: String, val userId: String
    ) {
        init {
            try {
                if (amount.toBigDecimal() <= BigDecimal.ZERO) {
                    throw IllegalArgumentException("Amount must be greater than zero")
                }
            } catch (e: NumberFormatException) {
                throw IllegalArgumentException("Amount must be a valid number")
            }
        }
    }
}