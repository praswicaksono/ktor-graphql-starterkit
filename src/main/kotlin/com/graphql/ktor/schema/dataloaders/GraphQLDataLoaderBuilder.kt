package com.graphql.ktor.schema.dataloaders

import com.expediagroup.graphql.dataloader.KotlinDataLoader
import org.koin.core.annotation.Single

@Single
class GraphQLDataLoaderBuilder {
    private val dataloaders: MutableList<KotlinDataLoader<Any, Any>> = mutableListOf()

    fun add(dataloader: KotlinDataLoader<Any, Any>) {
        dataloaders.add(dataloader)
    }

    fun build(): List<KotlinDataLoader<Any, Any>> = dataloaders
}