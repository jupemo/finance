package com.jupemo.finance.persistence.repository.jpa

import com.jupemo.finance.persistence.entity.UserDocument
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository
import org.bson.types.ObjectId

@MongoRepository
interface UserRepository : CrudRepository<UserDocument, ObjectId> {
}