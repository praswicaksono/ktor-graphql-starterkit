package com.graphql.ktor.schema.mutations

import com.expediagroup.graphql.server.operations.Mutation
import com.graphql.ktor.schema.models.User
import org.koin.core.annotation.Single

data class AuthPayload(val token: String? = null, val user: User? = null)

@Single
class LoginMutationService : Mutation {
    suspend fun login(email: String, password: String, aliasUUID: String?): AuthPayload {
        val token = "fake-token"
        val user = User(
            email = "fake@site.com",
            firstName = "Someone",
            lastName = "You Don't know",
            universityId = 4
        )
        return AuthPayload(token, user)
    }
}
