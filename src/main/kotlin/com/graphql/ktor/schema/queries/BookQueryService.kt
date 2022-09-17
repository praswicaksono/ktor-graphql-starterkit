package com.graphql.ktor.schema.queries

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import com.graphql.ktor.schema.models.Book
import org.koin.core.annotation.Single

@Single
class BookQueryService : Query {
    @GraphQLDescription("Return list of books based on BookSearchParameter options")
    @Suppress("unused")
    fun searchBooks(params: BookSearchParameters) = Book.search(params.ids)
}

data class BookSearchParameters(val ids: List<Int>)
