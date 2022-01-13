package com.example.justdesserts.shared.logger

actual class CustomLog {
    actual fun customLogMessage(message: String) {
        println(message)
    }
}
