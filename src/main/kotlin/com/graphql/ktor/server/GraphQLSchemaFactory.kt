package com.graphql.ktor.server

import com.expediagroup.graphql.generator.toSchema
import com.graphql.ktor.contracts.IFactory
import com.graphql.ktor.schema.mutations.GraphQLMutationBuilder
import com.graphql.ktor.schema.queries.GraphQLQueryBuilder
import com.graphql.ktor.schema.subscriptions.GraphQLSubscriptionBuilder
import graphql.schema.GraphQLSchema
import org.koin.core.annotation.Single

@Single
class GraphQLSchemaFactory(
    config: GraphQLSchemaGeneratorConfigFactory,
    queries: GraphQLQueryBuilder,
    mutations: GraphQLMutationBuilder,
    subscriptions: GraphQLSubscriptionBuilder
) : IFactory<GraphQLSchema> {

    private val graphQLSchema by lazy {
        toSchema(config.create(), queries.build(), mutations.build(), subscriptions.build())
    }

    override fun create(): GraphQLSchema = graphQLSchema
}