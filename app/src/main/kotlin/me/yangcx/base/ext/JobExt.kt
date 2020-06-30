package me.yangcx.base.ext

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren

suspend fun Job.cancelChildrenAndJoin(cause: CancellationException? = null) {
    cancelChildren(cause)
    children.forEach {
        it.join()
    }
}