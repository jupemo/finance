package com.jupemo.finance.persistence.mapper

import com.jupemo.finance.entity.User
import com.jupemo.finance.persistence.entity.UserDocument
import jakarta.inject.Singleton

@Singleton
class UserDocumentMapper {
    fun toUserDocument(user: User): UserDocument {
        return UserDocument(
            name =  user.getName(),
            email = user.getEmail()
        )
    }

    fun toUser(userDocument: UserDocument): User {
        val user = User(
            name = userDocument.name,
            email = userDocument.email
        )
        user.setId(userDocument.id.toString())
        return user
    }
}