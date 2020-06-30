package me.yangcx.base.ext

import kotlinx.coroutines.channels.SendChannel

suspend inline fun <reified DATA : Any> SendChannel<DATA>.sendIfOpen(data: DATA) {
    if (!isClosedForSend) {
        send(data)
    }
}