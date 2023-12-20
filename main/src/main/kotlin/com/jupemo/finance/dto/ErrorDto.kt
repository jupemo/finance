package com.jupemo.finance.dto

import io.micronaut.serde.annotation.Serdeable
import io.micronaut.serde.annotation.Serdeable.Deserializable

@Serdeable.Serializable
@Deserializable
data class ErrorDto(val message : String)
