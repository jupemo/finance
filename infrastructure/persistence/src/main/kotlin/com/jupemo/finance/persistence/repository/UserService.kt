package com.jupemo.finance.persistence.repository

import com.jupemo.finance.application.port.output.UserSavePort
import com.jupemo.finance.entity.User
import com.jupemo.finance.persistence.mapper.UserDocumentMapper
import com.jupemo.finance.persistence.repository.jpa.UserRepository
import com.mongodb.client.MongoClient
import com.mongodb.client.model.IndexOptions
import com.mongodb.client.model.Indexes
import jakarta.annotation.PostConstruct
import jakarta.inject.Singleton

@Singleton
class UserService(
    private val repository: UserRepository,
    private val userDocumentMapper: UserDocumentMapper,
    private val mongoClient: MongoClient
) : UserSavePort {

    @PostConstruct
    fun createIndex() {
        mongoClient.getDatabase("finance")
            .getCollection("user")
            .createIndex(Indexes.ascending("email"), IndexOptions().unique(true))
    }

    override fun saveUser(user: User): User {
        return userDocumentMapper.toUser(repository.save(userDocumentMapper.toUserDocument(user)))
    }

}