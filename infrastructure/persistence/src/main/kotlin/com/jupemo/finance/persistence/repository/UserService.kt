package com.jupemo.finance.persistence.repository

import com.jupemo.finance.application.port.output.UserSavePort
import com.jupemo.finance.entity.User
import com.jupemo.finance.persistence.mapper.UserDocumentMapper
import com.jupemo.finance.persistence.repository.jpa.UserRepository
import jakarta.inject.Singleton

@Singleton
class UserService(
    private val repository: UserRepository,
    private val userDocumentMapper: UserDocumentMapper
) : UserSavePort {

    override fun saveUser(user: User): User {
        return userDocumentMapper.toUser(repository.save(userDocumentMapper.toUserDocument(user)))
    }

}