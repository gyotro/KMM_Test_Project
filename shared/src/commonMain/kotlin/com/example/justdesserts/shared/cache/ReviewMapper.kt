package com.example.justdesserts.shared.cache

import com.example.justdesserts.GetDessertQuery
import com.example.justdesserts.PostReviewMutation

fun GetDessertQuery.Review.toReview() = Review(
    id = id,
    rating = rating.toLong(),
    dessert = dessertId,
    text = text,
    userId = userId
)

fun PostReviewMutation.PostReview.toReview() = Review(
    id = id,
    rating = rating.toLong(),
    dessert = dessertId,
    text = text,
    userId = userId
)
