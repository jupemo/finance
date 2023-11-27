package com.jupemo.finance.application

import jakarta.inject.Singleton

@Singleton
class UseCaseImpl : UseCase {
    override fun execute(): String {
        return "Hello World"
    }
}