import com.apollographql.apollo.api.ApolloExperimental
import com.example.justdesserts.shared.ApolloProvider

open class BaseRepository(apolloProvider: ApolloProvider) {
    @ApolloExperimental
    // prendiamo queste due variabili dall'Apollo Provider
    val apolloClient = apolloProvider.apolloClient
    val database = apolloProvider.database
}
