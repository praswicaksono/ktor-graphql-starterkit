package com.graphql.ktor.schema.queries

import com.expediagroup.graphql.server.operations.Query
import com.graphql.ktor.schema.models.University
import graphql.GraphQLException
import org.koin.core.annotation.Single

@Single
class UniversityQueryService : Query {
    @Throws(GraphQLException::class)
    suspend fun searchUniversities(params: UniversitySearchParameters): List<University> =
        University.search(params.ids)
}

data class UniversitySearchParameters(val ids: List<Int>)
