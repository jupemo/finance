package com.jupemo.finance.application.port.output

interface UserPort {
    fun getUser(): String

    fun saveUser(): String
}