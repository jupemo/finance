package com.jupemo.finance.dto

import io.micronaut.serde.annotation.Serdeable
import io.micronaut.serde.annotation.Serdeable.Deserializable
import jakarta.validation.constraints.NotBlank

@Serdeable.Serializable
@Deserializable
data class AmountDto(@NotBlank var amount: String)
