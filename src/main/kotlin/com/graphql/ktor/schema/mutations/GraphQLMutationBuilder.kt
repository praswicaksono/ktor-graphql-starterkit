package com.graphql.ktor.schema.mutations

import com.expediagroup.graphql.generator.TopLevelObject
import com.expediagroup.graphql.server.operations.Mutation
import org.koin.core.annotation.Single

@Single
class GraphQLMutationBuilder {
    private val mutations: MutableList<TopLevelObject> = mutableListOf()

    fun add(mutation: Mutation) {
        mutations.add(TopLevelObject(mutation))
    }

    fun build(): List<TopLevelObject> = mutations
}