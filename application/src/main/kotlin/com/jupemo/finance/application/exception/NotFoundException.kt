package com.jupemo.finance.application.exception

import java.lang.Exception

class NotFoundException(override val message: String) : Exception() {
}