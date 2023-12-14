package com.jupemo.finance.application.port.input

interface GetUserByEmailUseCase {
    fun getUserByEmail(email: String): Return

    data class Return(
        val id: String,
        val name: String,
        val email: String,
    )
}