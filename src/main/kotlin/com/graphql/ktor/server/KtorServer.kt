package com.graphql.ktor.server

import com.expediagroup.graphql.server.execution.GraphQLServer
import com.fasterxml.jackson.databind.ObjectMapper
import com.graphql.ktor.services.JacksonObjectMapperFactory
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single
class KtorServer(
    graphQLServerFactory: KtorGraphQLServerFactory,
    objectMapperFactory: JacksonObjectMapperFactory
) {

    private val ktorGraphQLServer: GraphQLServer<ApplicationRequest> by lazy {
        graphQLServerFactory.create()
    }

    private val objectMapper: ObjectMapper by lazy {
        objectMapperFactory.create()
    }

    suspend fun handle(applicationCall: ApplicationCall) {
        withContext(Dispatchers.IO) {

            val job = async {
                // Execute the query against the schema
                ktorGraphQLServer.execute(applicationCall.request)
            }

            val result = job.await()

            if (result == null) {
                applicationCall.response.call.respond(HttpStatusCode.BadRequest, "Invalid request")
            }

            val json = objectMapper.writeValueAsString(result)
            applicationCall.response.call.respond(json)
        }
    }
}