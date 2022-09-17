package com.graphql.ktor.schema.models

import com.expediagroup.graphql.server.extensions.getValueFromDataLoader
import com.graphql.ktor.schema.dataloaders.BookDataLoader
import com.graphql.ktor.schema.dataloaders.UniversityDataLoader
import graphql.schema.DataFetchingEnvironment
import java.util.concurrent.CompletableFuture

data class Course(
    val id: Int,
    val name: String? = null,
    val universityId: Int? = null,
    val bookIds: List<Int> = listOf()
) {
    fun university(dataFetchingEnvironment: DataFetchingEnvironment): CompletableFuture<University?> {
        return if (universityId != null) {
            dataFetchingEnvironment.getValueFromDataLoader(UniversityDataLoader.dataLoaderName, universityId)
        } else CompletableFuture.completedFuture(null)
    }

    fun books(dataFetchingEnvironment: DataFetchingEnvironment): CompletableFuture<List<Book>> {
        return dataFetchingEnvironment.getValueFromDataLoader(BookDataLoader.dataLoaderName, bookIds)
    }

    companion object {
        fun search(ids: List<Int>): List<Course> {
            return listOf(
                Course(id = 1, name = "Biology 101", universityId = 1, bookIds = listOf(1, 2)),
                Course(id = 2, name = "Cultural Anthropology", universityId = 1),
                Course(id = 3, name = "Computer Science 101", universityId = 1, bookIds = listOf(3, 4)),
                Course(id = 4, name = "Computer Science 101", universityId = 3, bookIds = listOf(3, 4))
            ).filter { ids.contains(it.id) }
        }
    }
}
