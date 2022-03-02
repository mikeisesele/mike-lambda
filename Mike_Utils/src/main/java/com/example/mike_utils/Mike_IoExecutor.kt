package com.example.mike_utils

import java.util.concurrent.Executors

/**
 * Utility method to run blocks on a dedicated background thread, used for io/database work. IoExecutor.runOnIoThread( yourFunctionHere() )
 */
object Mike_IoExecutor {
    private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

    fun runOnIoThread(f: () -> Unit) {
        IO_EXECUTOR.execute(f)
    }
}