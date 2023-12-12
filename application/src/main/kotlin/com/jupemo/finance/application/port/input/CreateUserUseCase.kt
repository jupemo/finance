package com.jupemo.finance.application.port.input

import com.jupemo.finance.entity.User

interface CreateUserUseCase {

    fun createUser(command: Command) : User
    data class Command(
        val name: String,
        val email: String
    )
}