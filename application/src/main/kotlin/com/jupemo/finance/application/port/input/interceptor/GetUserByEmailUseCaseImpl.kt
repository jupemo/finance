package com.jupemo.finance.application.port.input.interceptor

import com.jupemo.finance.application.exception.NotFoundException
import com.jupemo.finance.application.port.input.GetUserByEmailUseCase
import com.jupemo.finance.application.port.output.GetUserByEmailPort
import jakarta.inject.Singleton

@Singleton
class GetUserByEmailUseCaseImpl(
    private val getUserPort: GetUserByEmailPort
) : GetUserByEmailUseCase {
    override fun getUserByEmail(email: String): GetUserByEmailUseCase.Return {
        val user =
            getUserPort.getUserByEmail(email) ?: throw NotFoundException("User with email '$email' not found.")
        return GetUserByEmailUseCase.Return(
            id = user.id()!!,
            name = user.name(),
            email = user.email()
        )
    }

}