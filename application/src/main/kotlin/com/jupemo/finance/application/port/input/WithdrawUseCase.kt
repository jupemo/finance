package com.jupemo.finance.application.port.input

import java.math.BigDecimal

interface WithdrawUseCase {

    fun withdraw(command: Command)

    data class Command(
        val userId: String,
        val bankAccountId: String,
        val amount: String,
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