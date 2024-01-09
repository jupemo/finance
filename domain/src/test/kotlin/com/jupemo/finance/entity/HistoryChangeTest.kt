package com.jupemo.finance.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class HistoryChangeTest {

    @Test
    fun `should create historyChange`() {
        // given
        val amount = BigDecimal("100")
        val action = ActionType.DEPOSIT
        // when
        val historyChange = HistoryChange(amount, action)
        // then
        assertEquals(amount, historyChange.amount())
        assertEquals(action, historyChange.action())
    }

}