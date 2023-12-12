package com.jupemo.finance.controller

import com.fasterxml.jackson.annotation.JsonValue
import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Introspected
@Serdeable.Deserializable
@Serdeable.Serializable
data class UserDto( val name: String?,  val email: String?)
