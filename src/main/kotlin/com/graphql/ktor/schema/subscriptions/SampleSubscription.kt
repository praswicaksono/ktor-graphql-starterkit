package com.graphql.ktor.schema.subscriptions

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Subscription
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SampleSubscription: Subscription {

    @GraphQLDescription("Returns a single value")
    public fun sample(): Flow<Int> {
        return flowOf(1)
    }
}