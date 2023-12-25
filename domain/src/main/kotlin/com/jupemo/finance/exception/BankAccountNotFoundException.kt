package com.jupemo.finance.exception

class BankAccountNotFoundException(id: String) : Exception("Bank account with id $id not found") {
}