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
@Requires(classes = [BadRequestException::class, ExceptionHandler::class])
class BadRequestExceptionHandler : ExceptionHandler<BadRequestException, HttpResponse<ErrorDto>> {
    override fun handle(request: HttpRequest<*>?, exception: BadRequestException?): HttpResponse<ErrorDto> {
        val errorDto = exception?.let { ErrorDto(it.message!!) }
        return HttpResponse.badRequest<ErrorDto?>().body(errorDto)
    }
}