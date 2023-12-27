package com.jupemo.finance.exception

class BankAccountNotFoundException(id: String) : NotFoundException("Bank account with id $id not found") {
}