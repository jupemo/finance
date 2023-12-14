package com.jupemo.finance.controller

import com.jupemo.finance.application.port.input.CreateUserUseCase
import com.jupemo.finance.application.port.input.GetUserByEmailUseCase
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.http.hateoas.JsonError
import io.micronaut.validation.Validated
import jakarta.validation.Valid

@Validated
@Controller("/")
class UserController(
    private val createUserUseCase: CreateUserUseCase,
    private val getUserByEmailUseCase: GetUserByEmailUseCase
) {

    @Post("/user")
    @Produces(MediaType.APPLICATION_JSON)
    fun createUser(@Body @Valid user: UserDto): UserDto {
        val userCreated =
            createUserUseCase.createUser(CreateUserUseCase.Command(user.name, user.email))
        return UserDto(id = userCreated.id(), name = userCreated.name(), email = userCreated.email())
    }

    @Get("/user/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getUserByEmail(@PathVariable email: String): UserDto {
        val user = getUserByEmailUseCase.getUserByEmail(email)
        return UserDto(id = user.id, name = user.name, email = user.email)
    }

    @Error(global = true)
    fun error(request: HttpRequest<*>, e: Throwable): HttpResponse<JsonError> {
        val error = JsonError("Bad Things Happened: ${e.message}")

        return HttpResponse.badRequest<JsonError>()
            .body(error) // (3)
    }

}