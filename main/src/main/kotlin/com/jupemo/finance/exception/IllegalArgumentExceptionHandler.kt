package com.jupemo.finance.exception

import com.jupemo.finance.dto.ErrorDto
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton

@Produces
@Singleton
@Requires(classes = [IllegalArgumentException::class, ExceptionHandler::class])
class IllegalArgumentExceptionHandler : ExceptionHandler<IllegalArgumentException, HttpResponse<ErrorDto>> {
    override fun handle(request: HttpRequest<*>?, exception: IllegalArgumentException?): HttpResponse<ErrorDto> {
        return if (exception != null) {
            ErrorDto(exception.message!!).let { HttpResponse.badRequest(it) }
        } else {
            ErrorDto("Illegal Argument Exception").let { HttpResponse.badRequest() }
        }
    }
}