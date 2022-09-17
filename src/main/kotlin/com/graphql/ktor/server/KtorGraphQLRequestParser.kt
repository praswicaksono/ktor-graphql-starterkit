/*
 * Copyright 2022 Expedia, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.graphql.ktor.server

import com.expediagroup.graphql.server.execution.GraphQLRequestParser
import com.expediagroup.graphql.server.types.GraphQLServerRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.graphql.ktor.services.ObjectMapperFactory
import io.ktor.server.request.*
import org.koin.core.annotation.Single
import java.io.IOException

@Single
class KtorGraphQLRequestParser(
    objectMapperFactory: ObjectMapperFactory
) : GraphQLRequestParser<ApplicationRequest> {

    private val mapper: ObjectMapper = objectMapperFactory.buildJacksonObjectMapper()

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun parseRequest(request: ApplicationRequest): GraphQLServerRequest = try {
        val rawRequest = request.call.receiveText()
        mapper.readValue(rawRequest, GraphQLServerRequest::class.java)
    } catch (e: IOException) {
        throw IOException("Unable to parse GraphQL payload.")
    }
}
