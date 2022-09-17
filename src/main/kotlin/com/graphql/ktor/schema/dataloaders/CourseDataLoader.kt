package com.graphql.ktor.schema.dataloaders

import com.expediagroup.graphql.dataloader.KotlinDataLoader
import com.graphql.ktor.schema.models.Course
import kotlinx.coroutines.runBlocking
import org.dataloader.DataLoader
import org.dataloader.DataLoaderFactory
import org.koin.core.annotation.Single
import java.util.concurrent.CompletableFuture

@Single
class CourseDataLoader : KotlinDataLoader<Int, Course?> {
    companion object {
        const val dataLoaderName = "COURSE_LOADER"
    }

    override val dataLoaderName = "COURSE_LOADER"
    override fun getDataLoader(): DataLoader<Int, Course?> = DataLoaderFactory.newDataLoader<Int, Course?> { ids ->
        CompletableFuture.supplyAsync {
            runBlocking { Course.search(ids).toMutableList() }
        }
    }
}
