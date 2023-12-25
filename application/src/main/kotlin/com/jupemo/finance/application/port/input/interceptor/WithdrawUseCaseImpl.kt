package com.jupemo.finance.application.port.input.interceptor

import com.jupemo.finance.application.exception.NotFoundException
import com.jupemo.finance.application.port.input.WithdrawUseCase
import com.jupemo.finance.application.port.output.UserGetByIdPort
import com.jupemo.finance.application.port.output.UserUpdatePort
import jakarta.inject.Singleton
import java.math.BigDecimal

@Singleton
class WithdrawUseCaseImpl(
    private val userGetByIdPort: UserGetByIdPort,
    private val userUpdatePort: UserUpdatePort,
) : WithdrawUseCase {
    override fun withdraw(command: WithdrawUseCase.Command) {
        val user = userGetByIdPort.getUserById(command.userId)
            ?: throw NotFoundException("User with id '${command.userId}'not found")
        user.withdrawMoney(BigDecimal(command.amount), command.bankAccountId)
        userUpdatePort.updateUser(user)
    }
}