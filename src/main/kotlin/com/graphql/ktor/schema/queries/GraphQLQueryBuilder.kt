package com.graphql.ktor.schema.queries

import com.expediagroup.graphql.generator.TopLevelObject
import com.expediagroup.graphql.server.operations.Query
import org.koin.core.annotation.Single

@Single()
class GraphQLQueryBuilder {
    private val queries: MutableList<TopLevelObject> = mutableListOf()

    fun addQuery(query: Query) {
        queries.add(TopLevelObject(query))
    }

    fun build(): List<TopLevelObject> = queries
}