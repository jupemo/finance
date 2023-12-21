package com.jupemo.finance.application.port.output

import com.jupemo.finance.entity.User

interface UserGetByEmailPort {
    fun getUserByEmail(email: String): User?
}