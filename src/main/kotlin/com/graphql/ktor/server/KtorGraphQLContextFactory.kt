package com.graphql.ktor.server

import com.expediagroup.graphql.generator.execution.GraphQLContext
import com.expediagroup.graphql.server.execution.GraphQLContextFactory
import com.graphql.ktor.schema.models.User
import io.ktor.server.request.*
import org.koin.core.annotation.Single

@Single
class KtorGraphQLContextFactory : GraphQLContextFactory<GraphQLContext, ApplicationRequest> {

    override suspend fun generateContextMap(request: ApplicationRequest): Map<Any, Any> = mutableMapOf<Any, Any>(
        "user" to User(
            email = "fake@site.com",
            firstName = "Someone",
            lastName = "You Don't know",
            universityId = 4
        )
    ).also { map ->
        request.headers["my-custom-header"]?.let { customHeader ->
            map["customHeader"] = customHeader
        }
    }
}
