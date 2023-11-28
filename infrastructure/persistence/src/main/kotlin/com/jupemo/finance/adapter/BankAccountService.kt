package com.jupemo.finance.adapter

import com.jupemo.finance.application.port.output.BankAccountAdapter
import jakarta.inject.Singleton

@Singleton
class BankAccountService : BankAccountAdapter {
    override fun getBankAccount(): String {
        return "Hello world"
    }
}