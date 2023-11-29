package com.jupemo.finance.persistence.entity

import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import org.bson.types.ObjectId

@MappedEntity
@Introspected
data class UserDocument(
    @Id
    @GeneratedValue
    var id : ObjectId? = null,
    var name : String,
)
