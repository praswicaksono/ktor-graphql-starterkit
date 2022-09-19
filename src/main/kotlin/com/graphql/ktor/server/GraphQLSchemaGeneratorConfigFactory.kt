package com.graphql.ktor.server

import com.expediagroup.graphql.generator.SchemaGeneratorConfig
import com.expediagroup.graphql.generator.hooks.FlowSubscriptionSchemaGeneratorHooks
import com.graphql.ktor.contracts.IFactory
import org.koin.core.annotation.Single

@Single
class GraphQLSchemaGeneratorConfigFactory : IFactory<SchemaGeneratorConfig> {
    private val gqlSchemaGeneratorConfig : SchemaGeneratorConfig by lazy {
        SchemaGeneratorConfig(
            supportedPackages = listOf("com.graphql.ktor"),
            hooks = FlowSubscriptionSchemaGeneratorHooks()
        )
    }

    override fun create() : SchemaGeneratorConfig = gqlSchemaGeneratorConfig
}