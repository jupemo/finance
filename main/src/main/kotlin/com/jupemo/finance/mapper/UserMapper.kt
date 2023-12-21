package com.jupemo.finance.mapper

import com.jupemo.finance.dto.BankAccountDto
import com.jupemo.finance.dto.UserInfoDto
import com.jupemo.finance.entity.BankAccountType
import com.jupemo.finance.entity.User
import jakarta.inject.Singleton

@Singleton
class UserMapper {
    fun userToDto(user: User): UserInfoDto {
        return UserInfoDto(
            id = user.id()!!,
            name = user.name(),
            email = user.email(),
            bankAccounts = user.bankAccounts().map {
                BankAccountDto(
                    id = it.id(),
                    name = it.name(),
                    currency = it.currency(),
                    type = BankAccountType.valueOf(it.type().toString()),
                    bank = it.bank()
                )
            },
            version = user.version()!!
        )
    }

}