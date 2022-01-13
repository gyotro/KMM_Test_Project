import com.example.justdesserts.shared.cache.DatabaseDriverFactory
import com.example.justdesserts.shared.cache.Dessert
import com.example.justdesserts.shared.cache.JustDesserts
import com.example.justdesserts.shared.cache.UserState

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
