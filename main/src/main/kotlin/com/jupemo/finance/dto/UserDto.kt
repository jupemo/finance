package com.jupemo.finance.controller

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.NotBlank

@Introspected
@Serdeable.Deserializable
@Serdeable.Serializable
data class UserDto(val id: String? = null, @NotBlank val name: String, @NotBlank val email: String)
