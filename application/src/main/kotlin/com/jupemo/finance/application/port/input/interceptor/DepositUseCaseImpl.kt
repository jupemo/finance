package com.jupemo.finance.application.port.input.interceptor

import com.jupemo.finance.application.exception.UserNotFoundException
import com.jupemo.finance.application.port.input.DepositUseCase
import com.jupemo.finance.application.port.output.UserGetByIdPort
import com.jupemo.finance.application.port.output.UserUpdatePort
import jakarta.inject.Singleton
import java.math.BigDecimal

@Singleton
class DepositUseCaseImpl(
    private val userGetByIdPort: UserGetByIdPort,
    private val userUpdatePort: UserUpdatePort
) : DepositUseCase {
    override fun deposit(command: DepositUseCase.Command) {
        val user = userGetByIdPort.getUserById(command.userId)
            ?: throw UserNotFoundException("User with id: ${command.userId} found")
        user.depositMoney(BigDecimal(command.amount), command.bankAccountId)
        userUpdatePort.updateUser(user)
    }
}