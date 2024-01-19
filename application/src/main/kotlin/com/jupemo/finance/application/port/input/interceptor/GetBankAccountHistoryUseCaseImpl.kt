package com.jupemo.finance.application.port.input.interceptor

import com.jupemo.finance.application.port.input.GetBankAccountHistoryUseCase
import com.jupemo.finance.application.port.output.UserGetByIdPort
import com.jupemo.finance.entity.HistoryChange
import jakarta.inject.Singleton

@Singleton
class GetBankAccountHistoryUseCaseImpl(private val userGetById: UserGetByIdPort) : GetBankAccountHistoryUseCase {

    // move the pagination to the infrastructure layer
    override fun getBankAccountHistory(command: GetBankAccountHistoryUseCase.Command): List<HistoryChange> {
        val user = userGetById.getUserById(command.userId)
        val history = user?.bankAccounts().let { bankAccounts ->
            bankAccounts?.filter { it.id() == command.bankAccountId }
                ?.map { it.history() }
        }
        val pageStart = command.page * command.size
        val pageEnd = pageStart + command.size

        if (history.isNullOrEmpty()) return emptyList()
        if (history[0].size < pageStart) return emptyList()
        if (history[0].size < pageEnd) return history[0].subList(pageStart, history[0].size)

        return history[0].subList(pageStart, pageEnd)
    }
}