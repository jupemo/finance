package com.jupemo.finance.application.port.input.interceptor

import com.jupemo.finance.application.port.input.UseCase
import com.jupemo.finance.application.port.output.BankAccountAdapter
import jakarta.inject.Singleton

@Singleton
class UseCaseImpl (private val bankAccountAdapter: BankAccountAdapter) : UseCase {
    override fun execute(): String {
        return bankAccountAdapter.getBankAccount()
    }
}