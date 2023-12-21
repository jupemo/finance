package com.jupemo.finance.application.port.output

import com.jupemo.finance.entity.User

interface UserGetByIdPort {
    fun getUserById(id: String): User?
}