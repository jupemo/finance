package com.jupemo.finance.application.port.input.interceptor

import com.jupemo.finance.application.exception.UserNotFoundException
import com.jupemo.finance.application.port.input.GetUserByEmailUseCase
import com.jupemo.finance.application.port.output.UserGetByEmailPort
import jakarta.inject.Singleton

@Singleton
class GetUserByEmailUseCaseImpl(
    private val getUserPort: UserGetByEmailPort
) : GetUserByEmailUseCase {
    override fun getUserByEmail(email: String): GetUserByEmailUseCase.Return {
        val user =
            getUserPort.getUserByEmail(email) ?: throw UserNotFoundException("User with email '$email' not found.")
        return GetUserByEmailUseCase.Return(
            id = user.id()!!,
            name = user.name(),
            email = user.email()
        )
    }

}