package me.yangcx.base.repositories

import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import okhttp3.ResponseBody
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException
import son.ysy.photo.entities.response.ResponseErrorInfo
import son.ysy.photo.exceptions.ResponseException
import son.ysy.photo.exceptions.ResponseTokenException

abstract class BaseRepository : KoinComponent {
    private val moShi by inject<Moshi>()
    private fun readErrorInfoFromErrorBody(errorBody: ResponseBody): ResponseErrorInfo? {
        return moShi.adapter(ResponseErrorInfo::class.java)
            .fromJson(
                errorBody.string()
            )
    }

    protected fun <T : Any> Flow<T>.catchHttpException(): Flow<T> {
        return catch {
            var throwable: Throwable = it
            if (it is HttpException) {
                it.response()
                    ?.errorBody()
                    ?.run {
                        readErrorInfoFromErrorBody(this)
                    }?.run {
                        when (code) {
                            in 700..701 -> ResponseTokenException(code, message)
                            else -> ResponseException(code, message)

                        }
                    }?.apply {
                        throwable = this
                    }
            }
            throw throwable
        }
    }
}