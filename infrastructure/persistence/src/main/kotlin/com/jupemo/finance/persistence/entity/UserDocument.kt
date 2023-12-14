package com.jupemo.finance.persistence.entity

import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.Version
import org.bson.types.ObjectId

@MappedEntity("user")
@Introspected
data class UserDocument(
    @Id
    @GeneratedValue
    var id: ObjectId? = null,
    var name: String,
    var email: String,
    var bankAccounts: List<BankAccountDocument>? = null,
    @Version
    var version: Int? = null
)
