package com.jupemo.finance.persistence.entity

data class BankAccountDocument(
    val name: String,
    val currency: String,
    val type: String,
    val bank: String,
    val balance: String
)
