package com.jupemo.finance.application.port.output

import com.jupemo.finance.entity.User

interface UserGetPort {
    fun getUserById(id: String): User?
}