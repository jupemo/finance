package com.jupemo.finance.controller

import com.jupemo.finance.application.port.input.UseCase
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces

@Controller("/hello")
class FinanceController (private val useCase : UseCase) {

    @Get
    @Produces(MediaType.TEXT_PLAIN)
    fun index() = useCase.execute()
}