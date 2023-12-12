package com.jupemo.finance.application.port.input.interceptor

import com.jupemo.finance.application.port.input.CreateUserUseCase
import com.jupemo.finance.application.port.output.UserSavePort
import com.jupemo.finance.entity.User
import jakarta.inject.Singleton

@Singleton
class CreateUserUseCaseImpl(
    private val userSavePort: UserSavePort
) : CreateUserUseCase {
    override fun createUser(command: CreateUserUseCase.Command): User {
        return userSavePort.saveUser(User(command.name, command.email))
    }
}