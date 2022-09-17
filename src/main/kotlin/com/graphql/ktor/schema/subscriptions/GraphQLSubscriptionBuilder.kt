package com.graphql.ktor.schema.subscriptions

import com.expediagroup.graphql.generator.TopLevelObject
import com.expediagroup.graphql.server.operations.Subscription
import org.koin.core.annotation.Single

@Single
class GraphQLSubscriptionBuilder {
    private val subscriptions: MutableList<TopLevelObject> = mutableListOf<TopLevelObject>()

    fun add(subscription: Subscription): Unit {
        subscriptions.add(TopLevelObject(subscription))
    }

    fun build(): List<TopLevelObject> = subscriptions
}