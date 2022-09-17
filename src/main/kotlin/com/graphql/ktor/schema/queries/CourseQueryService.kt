package com.graphql.ktor.schema.queries

import com.expediagroup.graphql.server.operations.Query
import com.graphql.ktor.schema.models.Course
import org.koin.core.annotation.Single

@Single
class CourseQueryService : Query {
    fun searchCourses(params: CourseSearchParameters) = Course.search(params.ids)
}

data class CourseSearchParameters(val ids: List<Int>)
