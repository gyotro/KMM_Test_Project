package com.example.justdesserts.shared.repository

import BaseRepository
import com.apollographql.apollo.api.ApolloExperimental
import com.example.justdesserts.*
import com.example.justdesserts.shared.ApolloProvider
import com.example.justdesserts.shared.cache.*
import com.example.justdesserts.type.DessertInput
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single

@ExperimentalCoroutinesApi
@ApolloExperimental
class DessertRepository(apolloProvider: ApolloProvider) : BaseRepository(apolloProvider) {
    // Effettuiamo la query GetPageQuery e parsiamo il risultato
    suspend fun getDesserts(page: Int, size: Int): Desserts? {
        val result = apolloClient.query(GetPageQuery(page, size))
            .execute().single()
        return result.data?.dessertsPage?.toDesserts()
    }

    suspend fun getDessertDetail(id: String): DessertDetail? {
        val result = apolloClient.query(GetDessertQuery(id))
            .execute().single()
        return result.data?.dessert?.toDessertDetail()
    }

    suspend fun newDessert(dessert: DessertInput): Dessert? {
        val result = apolloClient.mutate(AddDessertMutation(dessert))
            .execute().single()
        return result.data?.postDessert?.toDessert()
    }

    suspend fun updateDessert(id: String, dessert: DessertInput): Dessert? {
        val result = apolloClient.mutate(UpdateDessertMutation(dessert, id))
            .execute().single()
        return result.data?.updateDessert?.toDessert()
    }

    suspend fun deleteDessert(id: String): Boolean? {
        val result = apolloClient.mutate(DeleteDessertMutation(id))
            .execute().single()
        return result.data?.deleteDessert
    }

    /*
     ora scriviamo le funzioni che hanno il compito di richiamare la classe Database per interagire col DB
     */

    fun saveDessert(dessert: Dessert) {
        database.insertDessert(dessert)
    }

    fun updateFavouriteDessert(dessert: Dessert) {
        database.updateDessert(dessert)
    }

    fun deleteFavouriteDessert(id: String) {
        database.deleteDessert(id)
    }
    fun getFavouriteDessert(id: String) = database.getDessertById(id)

    fun getAllDesserts() = database.getDesserts()
}
