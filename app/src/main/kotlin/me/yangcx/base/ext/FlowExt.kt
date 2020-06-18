package me.yangcx.base.ext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.http.Body

private fun readErrorInfoFromErrorBody(errorBody: ResponseBody) {
    errorBody.string()
}

fun <T : Any> Flow<T>.catchHttpException() {
    catch {
        var throwable: Throwable = it
        if (it is HttpException) {
            it.response()?.errorBody()?.also { body ->
                readErrorInfoFromErrorBody(body)
            }
        }
        throw throwable
    }
}