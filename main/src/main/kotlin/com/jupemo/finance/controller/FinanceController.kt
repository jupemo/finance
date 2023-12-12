package com.jupemo.finance.controller

import com.jupemo.finance.application.port.input.CreateUserUseCase
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces

@Controller("/")
class FinanceController(private val createUserUseCase: CreateUserUseCase) {

    @Post("/user")
    @Produces(MediaType.APPLICATION_JSON)
    fun createUser(@Body user: UserDto): UserDto {
        val userCreated =
            createUserUseCase.createUser(CreateUserUseCase.Command(user.name.toString(), user.email.toString()))
        return UserDto(userCreated.name(), userCreated.email())
    }

}