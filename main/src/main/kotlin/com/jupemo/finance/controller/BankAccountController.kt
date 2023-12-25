package com.jupemo.finance.controller

import com.jupemo.finance.application.port.input.DepositUseCase
import com.jupemo.finance.application.port.input.WithdrawUseCase
import com.jupemo.finance.dto.AmountDto
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import jakarta.validation.Valid

@Validated
@Controller("/user")
class BankAccountController(
    private var depositUseCase: DepositUseCase,
    private var withdrawUseCase: WithdrawUseCase
) {

    @Post("/{userId}/bank-account/{bankAccountId}/deposit")
    fun deposit(
        @PathVariable userId: String,
        @PathVariable bankAccountId: String,
        @Valid @Body amount: AmountDto
    ): HttpResponse<*> {
        depositUseCase.deposit(DepositUseCase.Command(amount.amount, bankAccountId, userId))
        return HttpResponse.noContent<Void>()
    }

    @Post("/{userId}/bank-account/{bankAccountId}/withdraw")
    fun withdraw(
        @PathVariable userId: String,
        @PathVariable bankAccountId: String,
        @Valid @Body amount: AmountDto
    ): HttpResponse<*> {
        withdrawUseCase.withdraw(WithdrawUseCase.Command(userId, bankAccountId, amount.amount))
        return HttpResponse.noContent<Void>()
    }
}
