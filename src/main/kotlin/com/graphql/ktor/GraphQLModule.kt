package com.graphql.ktor

import com.expediagroup.graphql.dataloader.KotlinDataLoader
import com.expediagroup.graphql.generator.extensions.print
import com.graphql.ktor.di.GraphQLSchemaModule
import com.graphql.ktor.di.GraphQLServerModule
import com.graphql.ktor.di.ServicesModule
import com.graphql.ktor.schema.dataloaders.BookDataLoader
import com.graphql.ktor.schema.dataloaders.CourseDataLoader
import com.graphql.ktor.schema.dataloaders.GraphQLDataLoaderBuilder
import com.graphql.ktor.schema.dataloaders.UniversityDataLoader
import com.graphql.ktor.schema.mutations.GraphQLMutationBuilder
import com.graphql.ktor.schema.mutations.LoginMutationService
import com.graphql.ktor.schema.queries.*
import com.graphql.ktor.schema.subscriptions.GraphQLSubscriptionBuilder
import com.graphql.ktor.schema.subscriptions.SampleSubscription
import com.graphql.ktor.server.GraphQLSchemaFactory
import com.graphql.ktor.server.KtorServer
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.koin.ksp.generated.module
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin


/**
 * Setup GraphQL Queries
 */
fun Application.queryBuilder(builder: GraphQLQueryBuilder) {
    builder.addQuery(inject<BookQueryService>().value)
    builder.addQuery(inject<HelloQueryService>().value)
    builder.addQuery(inject<CourseQueryService>().value)
    builder.addQuery(inject<UniversityQueryService>().value)
}

/**
 * Setup GraphQL Mutation
 */
fun Application.mutationBuilder(builder: GraphQLMutationBuilder) {
    builder.add(inject<LoginMutationService>().value)
}

/**
 * Setup GraphQL Subscription
 */
fun Application.subscriptionBuilder(builder: GraphQLSubscriptionBuilder) {
    builder.add(inject<SampleSubscription>().value)
}

fun Application.dataloaderBuilder(builder: GraphQLDataLoaderBuilder) {
    builder.add(inject<CourseDataLoader>().value as KotlinDataLoader<Any, Any>)
    builder.add(inject<BookDataLoader>().value as KotlinDataLoader<Any, Any>)
    builder.add(inject<UniversityDataLoader>().value as KotlinDataLoader<Any, Any>)
}

fun Application.graphQLModule() {
    install(Koin) {
        modules(
            listOf(
                GraphQLServerModule().module,
                GraphQLSchemaModule().module,
                ServicesModule().module
            )
        )
    }

    this.queryBuilder(inject<GraphQLQueryBuilder>().value)
    this.mutationBuilder(inject<GraphQLMutationBuilder>().value)
    this.subscriptionBuilder(inject<GraphQLSubscriptionBuilder>().value)
    this.dataloaderBuilder(inject<GraphQLDataLoaderBuilder>().value)

    // initialize graphql ktor server
    val ktorServer by inject<KtorServer>()
    val graphQLSchemaFactory by inject<GraphQLSchemaFactory>()

    install(WebSockets)
    install(Routing)
    routing {
        webSocket("/subscriptions") {
            //@Todo: implement graphQL websocket subscription protocol
        }
        post("graphql") {
            ktorServer.handle(this.call)
        }

        get("sdl") {
            call.respondText(graphQLSchemaFactory.schema().print())
        }

        get("playground") {
            this.call.respondText(buildPlaygroundHtml("graphql", "subscriptions"), ContentType.Text.Html)
        }
    }
}

private fun buildPlaygroundHtml(graphQLEndpoint: String, subscriptionsEndpoint: String) =
    Application::class.java.classLoader.getResource("graphql-playground.html")?.readText()
        ?.replace("\${graphQLEndpoint}", graphQLEndpoint)
        ?.replace("\${subscriptionsEndpoint}", subscriptionsEndpoint)
        ?: throw IllegalStateException("graphql-playground.html cannot be found in the classpath")
