package com.jupemo.finance.application.port.input

import com.jupemo.finance.entity.User

interface GetUserByIdUseCase {
    fun getUserById(id: String): User
}