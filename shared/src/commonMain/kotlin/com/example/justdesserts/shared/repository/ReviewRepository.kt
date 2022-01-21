package com.example.justdesserts.shared.repository

import BaseRepository
import com.apollographql.apollo.api.ApolloExperimental
import com.example.justdesserts.PostReviewMutation
import com.example.justdesserts.shared.ApolloProvider
import com.example.justdesserts.shared.cache.Review
import com.example.justdesserts.shared.cache.toReview
import com.example.justdesserts.type.ReviewInput
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single

@ExperimentalCoroutinesApi
@ApolloExperimental
class ReviewRepository(apolloProvider: ApolloProvider) : BaseRepository(apolloProvider) {

    suspend fun postReview(dessertId: String, review: ReviewInput): Review? {
        return apolloClient.run {
            val result = this.mutate(PostReviewMutation(dessertId, review)).execute().single()
            result.data?.postReview?.toReview()
        }
    }

    fun insertReview(review: Review) {
        database.insertReview(review)
    }
}
