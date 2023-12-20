package com.jupemo.finance.application.port.output

import com.jupemo.finance.entity.User

interface UserUpdatePort {
    fun updateUser(user: User): User
}