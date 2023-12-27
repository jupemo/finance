package com.jupemo.finance.application.exception

import com.jupemo.finance.exception.NotFoundException

class UserNotFoundException(message: String) : NotFoundException(message) {
}