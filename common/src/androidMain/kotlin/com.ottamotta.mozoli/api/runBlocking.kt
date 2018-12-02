package com.ottamotta.mozoli.api

actual fun <T> runBlocking(block: suspend () -> T): T {
    return kotlinx.coroutines.runBlocking{ block() }
}