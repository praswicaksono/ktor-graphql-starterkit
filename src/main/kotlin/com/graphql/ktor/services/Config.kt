package com.graphql.ktor.services

import com.graphql.ktor.contracts.IFactory
import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addResourceSource
import org.koin.core.annotation.Single

data class Database(val mongoDsn: String)

data class Server(val enablePlayground: Boolean)

data class Config(val env: String, val server: Server, val database: Database)

@Single
class ConfigFactory : IFactory<Config> {
    private val config : Config by lazy {
        ConfigLoaderBuilder.default()
            .addResourceSource("/settings.yml")
            .build()
            .loadConfigOrThrow<Config>()
    }

    override fun create(): Config = config
}