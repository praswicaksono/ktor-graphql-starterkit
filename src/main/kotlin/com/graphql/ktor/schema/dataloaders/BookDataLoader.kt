package com.graphql.ktor.schema.dataloaders

import com.expediagroup.graphql.dataloader.KotlinDataLoader
import com.graphql.ktor.schema.models.Book
import kotlinx.coroutines.runBlocking
import org.dataloader.DataLoader
import org.dataloader.DataLoaderFactory
import org.koin.core.annotation.Single
import java.util.concurrent.CompletableFuture

@Single
class BookDataLoader : KotlinDataLoader<List<Int>, List<Book>> {
    companion object {
        const val dataLoaderName = "BATCH_BOOK_LOADER"
    }

    override val dataLoaderName = BookDataLoader.dataLoaderName

    override fun getDataLoader(): DataLoader<List<Int>, List<Book>> =
        DataLoaderFactory.newDataLoader<List<Int>, List<Book>> { ids ->
            CompletableFuture.supplyAsync {
                val allBooks = runBlocking { Book.search(ids.flatten()).toMutableList() }
                // produce lists of results from returned books
                ids.fold(mutableListOf()) { acc: MutableList<List<Book>>, idSet ->
                    val matchingBooks = allBooks.filter { idSet.contains(it.id) }
                    acc.add(matchingBooks)
                    acc
                }
            }
        }
}
