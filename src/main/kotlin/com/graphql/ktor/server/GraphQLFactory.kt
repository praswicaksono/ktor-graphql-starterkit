package com.graphql.ktor.server

import com.expediagroup.graphql.generator.scalars.IDValueUnboxer
import com.expediagroup.graphql.generator.toSchema
import com.graphql.ktor.contracts.IFactory
import com.graphql.ktor.schema.mutations.GraphQLMutationBuilder
import com.graphql.ktor.schema.queries.GraphQLQueryBuilder
import com.graphql.ktor.schema.subscriptions.GraphQLSubscriptionBuilder
import graphql.GraphQL
import graphql.execution.SubscriptionExecutionStrategy
import graphql.schema.GraphQLSchema
import org.koin.core.annotation.Single

@Single
class GraphQLFactory(schema: GraphQLSchemaFactory) : IFactory<GraphQL> {

    private val graphQL by lazy {
        GraphQL.newGraphQL(
            schema.create()
        )
            .subscriptionExecutionStrategy(SubscriptionExecutionStrategy())
            .valueUnboxer(IDValueUnboxer())
            .build()
    }

    override fun create(): GraphQL = graphQL

}