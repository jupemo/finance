package com.jupemo.finance.exception

import com.jupemo.finance.application.exception.UserNotFoundException
import com.jupemo.finance.dto.ErrorDto
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton

@Produces
@Singleton
@Requires(classes = [UserNotFoundException::class, ExceptionHandler::class])
class UserNotFoundExceptionHandler : ExceptionHandler<UserNotFoundException, HttpResponse<ErrorDto>> {

    override fun handle(
        request: io.micronaut.http.HttpRequest<*>?,
        exception: UserNotFoundException?
    ): HttpResponse<ErrorDto> {
        val errorDto = exception?.let { ErrorDto(it.message) }
        return HttpResponse.notFound<ErrorDto?>().body(errorDto)
    }
}