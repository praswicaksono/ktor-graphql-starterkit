package com.graphql.ktor.server

import com.expediagroup.graphql.generator.SchemaGeneratorConfig
import com.expediagroup.graphql.generator.hooks.FlowSubscriptionSchemaGeneratorHooks
import org.koin.core.annotation.Single

@Single
class GraphQLSchemaGeneratorConfigFactory {
    fun create() = SchemaGeneratorConfig(
        supportedPackages = listOf("com.graphql.ktor"),
        hooks = FlowSubscriptionSchemaGeneratorHooks()
    )
}