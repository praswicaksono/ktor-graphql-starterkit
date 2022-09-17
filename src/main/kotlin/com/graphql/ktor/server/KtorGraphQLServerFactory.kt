package com.graphql.ktor.server

import com.expediagroup.graphql.dataloader.KotlinDataLoaderRegistryFactory
import com.expediagroup.graphql.server.execution.GraphQLRequestHandler
import com.expediagroup.graphql.server.execution.GraphQLServer
import com.graphql.ktor.schema.dataloaders.GraphQLDataLoaderBuilder
import io.ktor.server.request.*
import org.koin.core.annotation.Single

@Single
class KtorGraphQLServerFactory(
    private val requestParser: KtorGraphQLRequestParser,
    private val contextFactory: KtorGraphQLContextFactory,
    private val dataloaders: GraphQLDataLoaderBuilder,
    private val graphQLSchema: GraphQLSchemaFactory
) {

    fun build(): GraphQLServer<ApplicationRequest> {
        val requestHandler = GraphQLRequestHandler(
            graphQLSchema.create(),
            KotlinDataLoaderRegistryFactory(*dataloaders.build().toTypedArray())
        )

        return GraphQLServer(requestParser, contextFactory, requestHandler)
    }
}