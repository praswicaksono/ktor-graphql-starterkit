package com.graphql.ktor.server

import com.expediagroup.graphql.generator.scalars.IDValueUnboxer
import com.expediagroup.graphql.generator.toSchema
import com.graphql.ktor.schema.mutations.GraphQLMutationBuilder
import com.graphql.ktor.schema.queries.GraphQLQueryBuilder
import com.graphql.ktor.schema.subscriptions.GraphQLSubscriptionBuilder
import graphql.GraphQL
import graphql.execution.SubscriptionExecutionStrategy
import graphql.schema.GraphQLSchema
import org.koin.core.annotation.Single

@Single
class GraphQLSchemaFactory(
    private val config: GraphQLSchemaGeneratorConfigFactory,
    private val queries: GraphQLQueryBuilder,
    private val mutations: GraphQLMutationBuilder,
    private val subscriptions: GraphQLSubscriptionBuilder
) {
    fun create(): GraphQL {
        return GraphQL.newGraphQL(
            this.schema()
        )
            .subscriptionExecutionStrategy(SubscriptionExecutionStrategy())
            .valueUnboxer(IDValueUnboxer())
            .build()
    }

    fun schema(): GraphQLSchema = toSchema(config.create(), queries.build(), mutations.build(), subscriptions.build())
}