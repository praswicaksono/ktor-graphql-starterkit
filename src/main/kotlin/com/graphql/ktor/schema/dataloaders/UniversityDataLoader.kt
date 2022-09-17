package com.graphql.ktor.schema.dataloaders

import com.expediagroup.graphql.dataloader.KotlinDataLoader
import com.graphql.ktor.schema.models.University
import kotlinx.coroutines.runBlocking
import org.dataloader.DataLoader
import org.dataloader.DataLoaderFactory
import org.koin.core.annotation.Single
import java.util.concurrent.CompletableFuture

@Single
class UniversityDataLoader : KotlinDataLoader<Int, University?> {
    companion object {
        const val dataLoaderName = "UNIVERSITY_LOADER"
    }

    override val dataLoaderName = "UNIVERSITY_LOADER"
    override fun getDataLoader(): DataLoader<Int, University?> =
        DataLoaderFactory.newDataLoader<Int, University?> { ids ->
            CompletableFuture.supplyAsync {
                runBlocking { University.search(ids).toMutableList() }
            }
        }
}
