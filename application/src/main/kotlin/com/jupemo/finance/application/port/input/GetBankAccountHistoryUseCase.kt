package com.jupemo.finance.application.port.input

import com.jupemo.finance.entity.HistoryChange

interface GetBankAccountHistoryUseCase {
    fun getBankAccountHistory(command: Command): List<HistoryChange>

    data class Command(
        val userId: String,
        val bankAccountId: String,
        val page: Int = 0,
        val size: Int = 10
    )
}