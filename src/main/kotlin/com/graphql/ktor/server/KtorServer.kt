package com.graphql.ktor.server


import com.expediagroup.graphql.server.execution.GraphQLServer
import com.fasterxml.jackson.databind.ObjectMapper
import com.graphql.ktor.services.ObjectMapperFactory
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.koin.core.annotation.Single

@Single
class KtorServer(
    graphQLServerFactory: KtorGraphQLServerFactory,
    objectMapperFactory: ObjectMapperFactory
) {

    private val ktorGraphQLServer: GraphQLServer<ApplicationRequest> = graphQLServerFactory.build()
    private val objectMapper: ObjectMapper = objectMapperFactory.buildJacksonObjectMapper()

    /**
     * Handle incoming Ktor Http requests and send them back to the response methods.
     */
    suspend fun handle(applicationCall: ApplicationCall) {
        // Execute the query against the schema
        val result = ktorGraphQLServer.execute(applicationCall.request)

        if (result != null) {
            // write response as json
            val json = objectMapper.writeValueAsString(result)
            applicationCall.response.call.respond(json)
        } else {
            applicationCall.response.call.respond(HttpStatusCode.BadRequest, "Invalid request")
        }
    }
}