package com.graphql.ktor.schema.queries

import com.expediagroup.graphql.server.operations.Query
import org.koin.core.annotation.Single

@Single
class HelloQueryService : Query {
    fun hello() = "World!"
}
