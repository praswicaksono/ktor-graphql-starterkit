package com.graphql.ktor.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.koin.core.annotation.Single

@Single
class ObjectMapperFactory {
    fun buildJacksonObjectMapper(): ObjectMapper {
        return jacksonObjectMapper()
    }
}