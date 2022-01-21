import com.example.justdesserts.shared.cache.*

// attraverso questa classe andremo a richiamare gli statement SQL che sono creati nella cartella sqlDelight
class Database(databaseDriverFactory: DatabaseDriverFactory) {
/*
Sia le variabili, che le funzioni, sono autogenerate da Kotlin a partire dal file JustDesserts.sq
 */

    private val database = JustDesserts(databaseDriverFactory.createSQLDriver())
    private val dbQueries = database.justDessertsQueries

// ora scriveremo le varie funzioni che equivalgono alle query a DB (come se fosse un repository)

    internal fun clearDB() {
        dbQueries.transaction {
            dbQueries.removeAllDesserts()
        }
    }

    internal fun getDesserts(): List<Dessert> {
        return dbQueries.selectAllDesserts().executeAsList()
    }

    internal fun getDessertById(id: String): Dessert? {
        return dbQueries.selectById(id).executeAsOneOrNull()
    }

    internal fun insertDessert(dessert: Dessert) {
        dbQueries.transaction {
            postDessert(dessert)
        }
    }

    internal fun updateDessert(dessert: Dessert) {
        dbQueries.transaction {
            updateDessertById(dessert)
        }
    }

    internal fun deleteDessert(id: String) {
        dbQueries.transaction {
            dbQueries.removeById(id)
        }
    }

    internal fun getUserState(): UserState? {
        return dbQueries.selectUserState().executeAsOneOrNull()
    }

    internal fun deleteUserState() {
        dbQueries.transaction {
            dbQueries.removeUserState()
        }
    }

    internal fun saveUserState(userId: String, token: String) {
        dbQueries.transaction {
            insertNewUserState(userId, token)
        }
    }

    internal fun insertReview(review: Review){
        dbQueries.transaction {
            postNewReview(review)
        }
    }

    private fun postNewReview(review: Review) {
        dbQueries.insertNewReview(
            id = review.id,
            userId = review.userId,
            text = review.text,
            dessert = review.dessert,
            rating = review.rating
        )
    }

    private fun insertNewUserState(userId: String, token: String) {
        dbQueries.insertUserState(userId, token)
    }

    private fun updateDessertById(dessert: Dessert) {
        dbQueries.updateDessertById(dessert.name, dessert.description, dessert.url, dessert.id)
    }

    private fun postDessert(dessert: Dessert) {
        dbQueries.insertDessert(
            dessert.id,
            dessert.userId,
            dessert.name,
            dessert.description,
            dessert.url
        )
    }
}
