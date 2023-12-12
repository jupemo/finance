package com.jupemo.finance.persistence.mapper

import com.jupemo.finance.entity.BankAccount
import com.jupemo.finance.entity.BankAccountType
import com.jupemo.finance.entity.User
import com.jupemo.finance.persistence.entity.BankAccountDocument
import com.jupemo.finance.persistence.entity.UserDocument
import jakarta.inject.Singleton
import org.bson.types.ObjectId
import java.math.BigDecimal

@Singleton
class UserDocumentMapper {
    fun toUserDocument(user: User): UserDocument {
        val document = UserDocument(
            name = user.name(),
            email = user.email()
        )
        if (user.id() != null) document.id = ObjectId(user.id())
        if (user.bankAccounts().isNotEmpty()) document.bankAccounts = toBankAccountDocument(user)

        return document
    }

    fun toUser(userDocument: UserDocument): User {
        val user = User(
            name = userDocument.name,
            email = userDocument.email
        )
        user.id(userDocument.id.toString())
        if (userDocument.bankAccounts != null) user.bankAccounts(toBankAccount(userDocument))

        return user
    }

    private fun toBankAccountDocument(user: User): List<BankAccountDocument> {
        return user.bankAccounts().map {
            BankAccountDocument(
                name = it.name(),
                currency = it.currency(),
                type = it.type().toString(),
                bank = it.bank(),
                balance = it.balance().toString()
            )
        }
    }

    private fun toBankAccount(userDocument: UserDocument): List<BankAccount> {
        return userDocument.bankAccounts!!.map {
            BankAccount(
                name = it.name,
                currency = it.currency,
                type = BankAccountType.valueOf(it.type),
                bank = it.bank,
                balance = BigDecimal(it.balance)
            )
        }
    }

}