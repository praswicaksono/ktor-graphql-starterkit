package com.graphql.ktor.schema.queries

import com.expediagroup.graphql.generator.TopLevelObject
import com.expediagroup.graphql.server.operations.Query
import org.koin.core.annotation.Single

@Single
class GraphQLQueryBuilder {
    private val queries: MutableList<TopLevelObject> = mutableListOf<TopLevelObject>()

    fun addQuery(query: Query): Unit {
        queries.add(TopLevelObject(query))
    }

    fun build(): List<TopLevelObject> = queries
}