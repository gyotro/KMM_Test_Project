package com.example.justdesserts.shared.cache

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

// Implementazione della expect class
actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createSQLDriver(): SqlDriver {
        return AndroidSqliteDriver(JustDesserts.Schema, context, name = "desserts.db")
    }
}
