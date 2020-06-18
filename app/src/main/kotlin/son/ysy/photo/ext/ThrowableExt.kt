package son.ysy.photo.ext

import com.blankj.utilcode.util.StringUtils
import com.squareup.moshi.JsonDataException
import me.yangcx.base.exceptions.MessageThrowable
import retrofit2.HttpException
import son.ysy.photo.BuildConfig
import son.ysy.photo.R
import java.net.SocketException
import java.net.SocketTimeoutException

fun Throwable.getShowMessage(): String {
    return if (BuildConfig.DEBUG) {
        message ?: "no message"
    } else {
        when {
            this is MessageThrowable && !message.isNullOrBlank() -> message ?: "no message"
            this is SocketTimeoutException -> StringUtils.getString(R.string.string_error_message_timeout)
            this is SocketException || this is HttpException -> {
                StringUtils.getString(R.string.string_error_message_response_failed)
            }
            this is JsonDataException -> {
                StringUtils.getString(R.string.string_error_message_json)
            }
            else -> {
                StringUtils.getString(R.string.string_error_message_unknown)
            }
        }
    }
}
