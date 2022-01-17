package com.example.justdesserts.shared

import Database
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloExperimental
import com.apollographql.apollo.interceptor.BearerTokenInterceptor
import com.apollographql.apollo.interceptor.TokenProvider
import com.apollographql.apollo.network.http.ApolloHttpNetworkTransport
import com.example.justdesserts.shared.cache.DatabaseDriverFactory
import com.example.justdesserts.shared.logger.ApolloLoggerInterceptor
import com.example.justdesserts.shared.logger.CustomLog

// initializing Apollo Client: per utilizzare il token verrà estesa l'interfaccia TokenProvider, che
// ogniqualvolta si effettuerà una chiamata con questo client, provvederà ad invocare i metodi di createToken
// e refreshtoken (che peró non è utilizzato)
class ApolloProvider(databaseDriverFactory: DatabaseDriverFactory, customLog: CustomLog) : TokenProvider {

    @ApolloExperimental
    // richiamiamo il Logger
    internal val loggerInterceptor = ApolloLoggerInterceptor(customLog)
    val database = Database(databaseDriverFactory)
    @ApolloExperimental
    internal val apolloClient: ApolloClient = ApolloClient(
        networkTransport = ApolloHttpNetworkTransport(
            // URL of graphql service
            serverUrl = "http://localhost:8080/graphql",
            headers = mapOf(
                "Accept" to "application/Json",
                "Content-Type" to "application/Json"
            )
        ),
        interceptors = listOf(BearerTokenInterceptor(this), loggerInterceptor) // this perché si riferisce ai metodi di questa classe
    )

    // in questo modo prendiamo il token ed automaticamente l'interceptor ce lo mette nella chiamata
    override suspend fun currentToken(): String = database.getUserState()?.token ?: ""

    override suspend fun refreshToken(previousToken: String): String {
        return ""
    }
}
