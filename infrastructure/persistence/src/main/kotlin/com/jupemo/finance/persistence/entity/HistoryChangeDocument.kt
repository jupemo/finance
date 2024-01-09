package com.jupemo.finance.persistence.entity

import com.jupemo.finance.entity.ActionType
import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import java.time.LocalDateTime

@Introspected
@Serdeable.Deserializable
@Serdeable.Serializable
data class HistoryChangeDocument(
    val amount: String,
    val action: String,
    val date: LocalDateTime
)

