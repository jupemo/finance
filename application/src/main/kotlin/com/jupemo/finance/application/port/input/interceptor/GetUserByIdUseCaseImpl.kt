package com.jupemo.finance.application.port.input.interceptor

import com.jupemo.finance.application.exception.NotFoundException
import com.jupemo.finance.application.port.input.GetUserByIdUseCase
import com.jupemo.finance.application.port.output.UserGetByIdPort
import com.jupemo.finance.entity.User
import jakarta.inject.Singleton

@Singleton
class GetUserByIdUseCaseImpl(
    private val userGetByIdPort: UserGetByIdPort
) : GetUserByIdUseCase {
    override fun getUserById(id: String): User {
        return userGetByIdPort.getUserById(id) ?: throw NotFoundException("User with id '$id'not found")
    }
}