package com.graphql.ktor.contracts

interface IFactory<T> {
    fun create(): T
}