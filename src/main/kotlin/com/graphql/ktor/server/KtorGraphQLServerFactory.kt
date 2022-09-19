package com.graphql.ktor.server

import com.expediagroup.graphql.dataloader.KotlinDataLoaderRegistryFactory
import com.expediagroup.graphql.server.execution.GraphQLRequestHandler
import com.expediagroup.graphql.server.execution.GraphQLServer
import com.graphql.ktor.contracts.IFactory
import com.graphql.ktor.schema.dataloaders.GraphQLDataLoaderBuilder
import io.ktor.server.request.*
import org.koin.core.annotation.Single

@Single
class KtorGraphQLServerFactory(
    requestParser: KtorGraphQLRequestParser,
    contextFactory: KtorGraphQLContextFactory,
    dataloaders: GraphQLDataLoaderBuilder,
    graphQLSchema: GraphQLFactory
) : IFactory<GraphQLServer<ApplicationRequest>> {

    private val gqlServer : GraphQLServer<ApplicationRequest> by lazy {
        val requestHandler = GraphQLRequestHandler(
            graphQLSchema.create(),
            KotlinDataLoaderRegistryFactory(*dataloaders.build().toTypedArray())
        )

        GraphQLServer(requestParser, contextFactory, requestHandler)
    }

    override fun create(): GraphQLServer<ApplicationRequest> = gqlServer
}