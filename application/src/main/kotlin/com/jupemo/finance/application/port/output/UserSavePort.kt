package com.jupemo.finance.application.port.output

import com.jupemo.finance.entity.User
import jakarta.inject.Singleton

@Singleton
interface UserSavePort {
    fun saveUser(user : User): User
}