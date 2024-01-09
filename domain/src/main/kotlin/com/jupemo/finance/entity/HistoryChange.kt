package com.jupemo.finance.entity

import java.math.BigDecimal
import java.time.LocalDateTime

class HistoryChange(
    private val amount: BigDecimal,
    private val action: ActionType,
) {

    private val date: LocalDateTime = LocalDateTime.now()

    fun amount(): BigDecimal {
        return this.amount
    }

    fun action(): ActionType {
        return this.action
    }

    fun date(): LocalDateTime {
        return this.date
    }
}