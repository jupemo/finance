package com.jupemo.finance.controller

import com.jupemo.finance.application.port.input.CreateBankAccountUseCase
import com.jupemo.finance.application.port.input.CreateUserUseCase
import com.jupemo.finance.application.port.input.GetUserByEmailUseCase
import com.jupemo.finance.application.port.input.GetUserByIdUseCase
import com.jupemo.finance.dto.BankAccountDto
import com.jupemo.finance.dto.UserDto
import com.jupemo.finance.dto.UserInfoDto
import com.jupemo.finance.entity.BankAccountType
import com.jupemo.finance.mapper.UserMapper
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import jakarta.validation.Valid

@Validated
@Controller("/")
class UserController(
    private val createUserUseCase: CreateUserUseCase,
    private val getUserByEmailUseCase: GetUserByEmailUseCase,
    private val createBankAccountUseCase: CreateBankAccountUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val userMapper: UserMapper
) {

    @Post("/user")
    @Produces(MediaType.APPLICATION_JSON)
    fun createUser(@Body @Valid user: UserDto): UserDto {
        val userCreated =
            createUserUseCase.createUser(CreateUserUseCase.Command(user.name, user.email))
        return UserDto(id = userCreated.id(), name = userCreated.name(), email = userCreated.email())
    }

    @Get("/user/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getUserByEmail(@PathVariable email: String): UserDto {
        val user = getUserByEmailUseCase.getUserByEmail(email)
        return UserDto(id = user.id, name = user.name, email = user.email)
    }

    @Post("/user/{id}/bank-account")
    @Produces(MediaType.APPLICATION_JSON)
    fun createBankAccount(@PathVariable id: String, @Body @Valid bankAccountDto: BankAccountDto): BankAccountDto {
        val bankAccountCreated = createBankAccountUseCase.createBankAccount(
            CreateBankAccountUseCase.Command(
                userId = id,
                name = bankAccountDto.name,
                currency = bankAccountDto.currency,
                type = bankAccountDto.type.name,
                bank = bankAccountDto.bank
            )
        )
        return BankAccountDto(
            id = bankAccountCreated.id,
            name = bankAccountCreated.name,
            currency = bankAccountCreated.currency,
            type = BankAccountType.valueOf(bankAccountCreated.type),
            bank = bankAccountCreated.bank
        )
    }

    @Get("/user/{id}/info")
    @Produces(MediaType.APPLICATION_JSON)
    fun getUserById(@PathVariable id: String): UserInfoDto {
        return userMapper.userToDto(getUserByIdUseCase.getUserById(id))
    }
}