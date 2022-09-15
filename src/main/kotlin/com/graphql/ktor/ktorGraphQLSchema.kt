/*
 * Copyright 2021 Expedia, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.graphql.ktor

import com.graphql.ktor.schema.BookQueryService
import com.graphql.ktor.schema.CourseQueryService
import com.graphql.ktor.schema.HelloQueryService
import com.graphql.ktor.schema.LoginMutationService
import com.graphql.ktor.schema.UniversityQueryService
import com.expediagroup.graphql.generator.SchemaGeneratorConfig
import com.expediagroup.graphql.generator.TopLevelObject
import com.expediagroup.graphql.generator.execution.FlowSubscriptionExecutionStrategy
import com.expediagroup.graphql.generator.hooks.FlowSubscriptionSchemaGeneratorHooks
import com.expediagroup.graphql.generator.scalars.IDValueUnboxer
import com.expediagroup.graphql.generator.toSchema
import com.graphql.ktor.schema.subscriptions.SampleSubscription
import graphql.GraphQL

/**
 * Custom logic for how this Ktor server loads all the queries and configuration to create the [GraphQL] object
 * needed to handle incoming requests. In a more enterprise solution you may want to load more things from
 * configuration files instead of hardcoding them.
 */
private val config = SchemaGeneratorConfig(supportedPackages = listOf("com.graphql.ktor"), hooks = FlowSubscriptionSchemaGeneratorHooks())
private val queries = listOf(
    TopLevelObject(HelloQueryService()),
    TopLevelObject(BookQueryService()),
    TopLevelObject(CourseQueryService()),
    TopLevelObject(UniversityQueryService())
)
private val mutations = listOf(TopLevelObject(LoginMutationService()))

private val subscription = listOf(TopLevelObject(SampleSubscription()))
val graphQLSchema = toSchema(config, queries, mutations, subscription)

fun getGraphQLObject(): GraphQL = GraphQL.newGraphQL(graphQLSchema)
    .subscriptionExecutionStrategy(FlowSubscriptionExecutionStrategy())
    .valueUnboxer(IDValueUnboxer())
    .build()
