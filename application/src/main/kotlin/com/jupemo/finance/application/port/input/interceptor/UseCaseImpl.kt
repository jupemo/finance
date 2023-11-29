package com.jupemo.finance.application.port.input.interceptor

import com.jupemo.finance.application.port.input.UseCase
import com.jupemo.finance.application.port.output.UserPort
import jakarta.inject.Singleton

@Singleton
class UseCaseImpl (private val bankAccountAdapter: UserPort) : UseCase {
    override fun execute(): String {
        return bankAccountAdapter.saveUser()
    }
}