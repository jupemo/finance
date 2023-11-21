package com.jupemo.finance.controller

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces

@Controller("/hello")
class FinanceController {

    @Get
    @Produces(MediaType.TEXT_PLAIN)
    fun index() = "HelloWorld"
}