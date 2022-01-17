package com.example.justdesserts.shared.cache
// ktlint-disable filename

import com.example.justdesserts.GetDessertQuery
import com.example.justdesserts.GetPageQuery

/*
 in questo file faremo delle funzioni che serviranno per serializzare i dati in arrivo
 dalle query/mutation di graphQL in classi che abbiamo autogenerato tramite graphQL
 (nel package graphQL abbiamo importato le query e mutation che poi in fase di build hanno autogenerato le classi kotlin per il marshallinG
 */
// le seguenti sono delle semplici classi di appoggio per la serializzazione
data class Desserts(val result: List<Dessert>, val info: GetPageQuery.Info?)
data class DessertDetail(val dessert: Dessert, val review: List<Review>)

// creiamo delle extension function delle nostre classi autogenerate per aiutarci con il marshalling
fun GetPageQuery.Result.toDessert() = Dessert(
    id = this.id,
    userId = this.userId,
    description = this.description,
    name = this.name,
    url = this.url
)

fun GetPageQuery.DessertsPage.toDesserts() = Desserts(
    result = this.result.map { it.toDessert() },
    info = this.info
)

fun GetDessertQuery.Dessert.toDessert() = Dessert(
    id = id, userId = userId, description = description, name = name, url = url
)

fun GetDessertQuery.Dessert.toDessertDetail() = DessertDetail(
    dessert = this.toDessert(),
    review = emptyList()
)
