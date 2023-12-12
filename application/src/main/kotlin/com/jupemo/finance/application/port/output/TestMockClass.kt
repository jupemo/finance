package com.jupemo.finance.application.port.output

import com.jupemo.finance.entity.User
import jakarta.inject.Singleton

@Singleton
interface TestMockClass {

    fun testMock(user : User) : String {
        return "Hello world"
    }

}