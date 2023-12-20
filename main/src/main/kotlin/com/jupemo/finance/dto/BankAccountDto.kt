package com.jupemo.finance.dto

import com.jupemo.finance.entity.BankAccountType
import io.micronaut.serde.annotation.Serdeable
import io.micronaut.serde.annotation.Serdeable.Deserializable
import jakarta.validation.constraints.NotBlank

@Serdeable.Serializable
@Deserializable
data class BankAccountDto(
    @NotBlank val name: String,
    @NotBlank val currency: String,
    val type: BankAccountType,
    @NotBlank val bank: String,
    val id: String? = null
)
