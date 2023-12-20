package com.jupemo.finance.application.port.input

interface CreateBankAccountUseCase {

    fun createBankAccount(command: Command) : Return

    data class Command(
        val userId: String,
        val name: String,
        val currency: String,
        val type: String,
        val bank: String,
    ) {
        init {
            require(name.isNotBlank()) { "Name must not be blank" }
            require(currency.isNotBlank()) { "Currency must not be blank" }
            require(type.isNotBlank()) { "Type must not be blank" }
            require(bank.isNotBlank()) { "Bank must not be blank" }
        }
    }

    data class Return(
        val id: String,
        val name: String,
        val type: String,
        val bank: String,
        val currency: String,
    )

}