package com.jupemo.finance.application.port.input

interface DepositUseCase {
    fun deposit(command: Command)

    data class Command(
        val amount: String,
        val bankAccountId: String,
        val userId: String
    ) {
        init {
            try {
                amount.toBigDecimal()
            } catch (e: Exception) {
                throw IllegalArgumentException("Amount must be a valid number")
            }
        }
    }
}