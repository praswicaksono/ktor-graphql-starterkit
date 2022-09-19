package com.graphql.ktor.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.graphql.ktor.contracts.IFactory
import org.koin.core.annotation.Single

@Single
class JacksonObjectMapperFactory : IFactory<ObjectMapper> {
    private val mapper : ObjectMapper by lazy {
        jacksonObjectMapper()
    }
    override fun create(): ObjectMapper = mapper
}