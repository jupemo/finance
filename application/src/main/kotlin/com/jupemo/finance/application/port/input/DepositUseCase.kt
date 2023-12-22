package com.jupemo.finance.application.port.input

interface DepositUseCase {
    fun deposit(command: Command)

    data class Command(
        val amount: String,
        val bankAccountId: String,
        val userId: String
    )
}