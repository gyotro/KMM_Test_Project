package com.example.justdesserts.shared

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.http.ApolloHttpNetworkTransport

// initializing Apollo Client
class ApolloProvider {

    internal val apolloClient: ApolloClient = ApolloClient(
        networkTransport = ApolloHttpNetworkTransport(
            // URL of graphql service
            serverUrl = "http://localhost:8080/graphql",
            headers = mapOf(
                "Accept" to "application/Json",
                "Content-Type" to "application/Json"
            )
        )
    )
}
