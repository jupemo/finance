package com.jupemo.finance.controller

import com.jupemo.finance.BaseTest
import com.jupemo.finance.application.port.input.GetUserByEmailUseCase
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.inject.Inject
import org.junit.jupiter.api.Test
import java.util.*

@MicronautTest
class UserControllerGetByEmailTest : BaseTest() {

    @Inject
    @Client("/")
    lateinit var client: HttpClient

    @Inject
    lateinit var getUserByEmailUseCase: GetUserByEmailUseCase

    @MockBean(GetUserByEmailUseCase::class)
    fun getUserByEmailUseCase(): GetUserByEmailUseCase {
        return mockk(relaxed = true)
    }


    @Test
    fun `getUserByEmail should return a UserDto with the given name and email`() {
        val email = "test@email.com"
        val userDto = UserDto(name = "Test User", email = "test@email.com", id = UUID.randomUUID().toString())

        every { getUserByEmailUseCase.getUserByEmail(any()) } returns GetUserByEmailUseCase.Return(
            id = userDto.id!!,
            name = userDto.name,
            email = userDto.email
        )

        val request = HttpRequest.GET<UserDto>("/user/$email")
        client.toBlocking().exchange(request, UserDto::class.java)

        verify { getUserByEmailUseCase.getUserByEmail(email) }
    }

}