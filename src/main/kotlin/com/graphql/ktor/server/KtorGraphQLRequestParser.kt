package com.graphql.ktor.server

import com.expediagroup.graphql.server.execution.GraphQLRequestParser
import com.expediagroup.graphql.server.types.GraphQLServerRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.graphql.ktor.services.JacksonObjectMapperFactory
import io.ktor.server.request.*
import org.koin.core.annotation.Single
import java.io.IOException

@Single
class KtorGraphQLRequestParser(
    objectMapperFactory: JacksonObjectMapperFactory
) : GraphQLRequestParser<ApplicationRequest> {

    private val mapper: ObjectMapper by lazy {
        objectMapperFactory.create()
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun parseRequest(request: ApplicationRequest): GraphQLServerRequest = try {
        val rawRequest = request.call.receiveText()
        mapper.readValue(rawRequest, GraphQLServerRequest::class.java)
    } catch (e: IOException) {
        throw IOException("Unable to parse GraphQL payload.")
    }
}
