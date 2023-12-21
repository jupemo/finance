package com.jupemo.finance.dto

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import io.micronaut.serde.annotation.Serdeable.Deserializable

@Introspected
@Serdeable.Serializable
@Deserializable
data class UserInfoDto(
    val id: String,
    val name: String,
    val email: String,
    val bankAccounts: List<BankAccountDto> = emptyList(),
    val version: Int
)

