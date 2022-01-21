package com.example.justdesserts.shared.repository

import BaseRepository
import com.apollographql.apollo.api.ApolloExperimental
import com.example.justdesserts.GetProfileQuery
import com.example.justdesserts.SignInMutation
import com.example.justdesserts.SignUpMutation
import com.example.justdesserts.shared.ApolloProvider
import com.example.justdesserts.shared.cache.Dessert
import com.example.justdesserts.shared.cache.toDessert
import com.example.justdesserts.type.UserInput
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single

@ExperimentalCoroutinesApi
@ApolloExperimental
class AuthRepository(apolloProvider: ApolloProvider) : BaseRepository(apolloProvider) {

    suspend fun signIn(userInput: UserInput): String? {
        val result = apolloClient.mutate(SignInMutation(userInput)).execute().single()
        result.data?.let {
            // salviamo direttamente user e token a DB
            return it.signIn?.run {
                database.saveUserState(this.user.id, this.token) // si può anche omettere This
                token
            }
        }
        throw Exception("Can't sign in!!!")
    }
    suspend fun signUp(userInput: UserInput): String? {
        val result = apolloClient.mutate(SignUpMutation(userInput)).execute().single()
        result.data?.let {
            return it.signUp?.run {
                database.saveUserState(this.user.id, this.token) // si può anche omettere This
                token
            }
        }
        throw Exception("Can't sign up!!!")
    }

    suspend fun getProfile(): List<Dessert>? {
        val result = apolloClient.query(GetProfileQuery()).execute().single()
        return result.data?.getProfile?.desserts?.map { it.toDessert() }
    }

    fun getUserState() = database.getUserState()

    fun deleteUserState() = database.deleteUserState()
}
