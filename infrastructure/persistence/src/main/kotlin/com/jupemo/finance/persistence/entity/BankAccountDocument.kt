package com.jupemo.finance.persistence.entity

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Introspected
@Serdeable.Deserializable
@Serdeable.Serializable
data class BankAccountDocument(
    val id: String,
    val name: String,
    val currency: String,
    val type: String,
    val bank: String,
    val balance: String
)
