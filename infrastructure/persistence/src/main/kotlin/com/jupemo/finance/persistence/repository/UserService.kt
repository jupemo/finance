package com.jupemo.finance.persistence.repository

import com.jupemo.finance.application.port.output.UserPort
import com.jupemo.finance.persistence.entity.UserDocument
import com.jupemo.finance.persistence.repository.jpa.UserRepository
import jakarta.inject.Singleton
import org.bson.types.ObjectId

@Singleton
class UserService(
    private val repository: UserRepository
) : UserPort {
    override fun getUser(): String {
        return "Hello world"
    }

    override fun saveUser(): String {
        repository.save(UserDocument(ObjectId(), "name"))
        return "done"
    }

    fun saveBankAccount(user : UserDocument): UserDocument {
        return repository.save(user)
    }
}