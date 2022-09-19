package com.graphql.ktor.schema.dataloaders

import com.expediagroup.graphql.dataloader.KotlinDataLoader
import org.koin.core.annotation.Single

@Single
class GraphQLDataLoaderBuilder {
    private val dataloaders: MutableList<KotlinDataLoader<*, *>> = mutableListOf()

    fun add(dataloader: KotlinDataLoader<*, *>) {
        dataloaders.add(dataloader)
    }

    fun build(): List<KotlinDataLoader<*, *>> = dataloaders
}