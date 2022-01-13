package com.example.justdesserts.shared.logger

expect class CustomLog {
    // prende in input una stringa e la Stampa come log
    fun customLogMessage(message: String)
}