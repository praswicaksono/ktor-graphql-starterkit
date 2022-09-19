package com.graphql.ktor.services

import com.graphql.ktor.contracts.IFactory
import org.litote.kmongo.reactivestreams.*
import org.litote.kmongo.coroutine.*

class DatabaseFactory(configFactory: ConfigFactory) : IFactory<CoroutineClient> {
    private val db by lazy {
        KMongo.createClient(configFactory.create().database.mongoDsn).coroutine
    }

    override fun create(): CoroutineClient = db
}