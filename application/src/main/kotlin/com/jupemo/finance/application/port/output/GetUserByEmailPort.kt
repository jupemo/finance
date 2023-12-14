package com.jupemo.finance.application.port.output

import com.jupemo.finance.entity.User

interface GetUserByEmailPort {
    fun getUserByEmail(email: String): User?
}