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
            name = user.getName(),
            email = user.getEmail()
        )
        if (user.getId() != null) document.id = ObjectId(user.getId())
        if (user.getBankAccounts().isNotEmpty()) document.bankAccounts = toBankAccountDocument(user)

        return document
    }

    fun toUser(userDocument: UserDocument): User {
        val user = User(
            name = userDocument.name,
            email = userDocument.email
        )
        user.setId(userDocument.id.toString())
        if (userDocument.bankAccounts != null) user.setBankAccounts(toBankAccount(userDocument))

        return user
    }

    private fun toBankAccountDocument(user: User): List<BankAccountDocument> {
        return user.getBankAccounts().map {
            BankAccountDocument(
                name = it.getName(),
                currency = it.getCurrency(),
                type = it.getType().toString(),
                bank = it.getBank(),
                balance = it.getBalance().toString()
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