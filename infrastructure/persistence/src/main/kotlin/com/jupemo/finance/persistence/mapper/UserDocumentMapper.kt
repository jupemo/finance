package com.jupemo.finance.persistence.mapper

import com.jupemo.finance.entity.*
import com.jupemo.finance.persistence.entity.BankAccountDocument
import com.jupemo.finance.persistence.entity.HistoryChangeDocument
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
        if (user.version() != null) document.version = user.version()

        return document
    }

    fun toUser(userDocument: UserDocument): User {
        val user = User(
            name = userDocument.name,
            email = userDocument.email
        )
        user.id(userDocument.id.toString())
        user.version(userDocument.version)
        if (userDocument.bankAccounts != null) user.bankAccounts(toBankAccount(userDocument))

        return user
    }

    private fun toBankAccountDocument(user: User): List<BankAccountDocument> {
        return user.bankAccounts().map {
            BankAccountDocument(
                id = it.id(),
                name = it.name(),
                currency = it.currency(),
                type = it.type().toString(),
                bank = it.bank(),
                balance = it.balance().toString(),
                history = it.history()?.map { historyChange ->
                    toHistoryChangeDocument(historyChange)
                }
            )
        }
    }

    private fun toBankAccount(userDocument: UserDocument): List<BankAccount> {
        return userDocument.bankAccounts!!.map {
            BankAccount(
                id = it.id,
                name = it.name,
                currency = it.currency,
                type = BankAccountType.valueOf(it.type),
                bank = it.bank,
                balance = BigDecimal(it.balance),
                history = if (it.history != null) it.history.map { historyChangeDocument ->
                    toHistoryChange(historyChangeDocument)
                } else emptyList()
            )
        }
    }

    private fun toHistoryChange(historyChangeDocument: HistoryChangeDocument): HistoryChange {
        return HistoryChange(
            amount = BigDecimal(historyChangeDocument.amount),
            action = ActionType.valueOf(historyChangeDocument.action),
            date = historyChangeDocument.date
        )
    }

    private fun toHistoryChangeDocument(historyChange: HistoryChange): HistoryChangeDocument {
        return HistoryChangeDocument(
            amount = historyChange.amount().toString(),
            action = historyChange.action().toString(),
            date = historyChange.date()
        )
    }

}