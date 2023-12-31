package com.jupemo.finance.application.port.input.interceptor

import com.jupemo.finance.application.exception.UserNotFoundException
import com.jupemo.finance.application.port.input.CreateBankAccountUseCase
import com.jupemo.finance.application.port.output.UserGetByIdPort
import com.jupemo.finance.application.port.output.UserUpdatePort
import com.jupemo.finance.entity.BankAccountType
import jakarta.inject.Singleton
import java.util.*

@Singleton
class CreateBankAccountUseCaseImpl(
    private val userGetPort: UserGetByIdPort,
    private val userUpdatePort: UserUpdatePort
) : CreateBankAccountUseCase {
    override fun createBankAccount(command: CreateBankAccountUseCase.Command): CreateBankAccountUseCase.Return {
        val user = userGetPort.getUserById(command.userId)
            ?: throw UserNotFoundException("User with id '${command.userId}'not found")
        val bankAccount =
            user.createBankAccount(
                UUID.randomUUID().toString(),
                command.name,
                command.currency,
                BankAccountType.valueOf(command.type),
                command.bank
            )
        userUpdatePort.updateUser(user)
        return CreateBankAccountUseCase.Return(
            bankAccount.id(),
            bankAccount.name(),
            bankAccount.type().toString(),
            bankAccount.bank(),
            bankAccount.currency()
        )
    }
}