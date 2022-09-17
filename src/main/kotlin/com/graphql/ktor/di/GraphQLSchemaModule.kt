package com.graphql.ktor.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan("com.graphql.ktor.schema")
class GraphQLSchemaModule