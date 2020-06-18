package son.ysy.photo.utils

import com.blankj.utilcode.util.StringUtils
import org.json.JSONException
import org.koin.core.KoinComponent
import retrofit2.HttpException
import son.ysy.photo.R
import son.ysy.photo.exceptions.ResponseException

object ThrowableUtil : KoinComponent {

    fun Throwable.getShowMessage() = when (this) {
        is JSONException -> {
            StringUtils.getString(R.string.string_throwable_message_json)
        }
        is HttpException ->""
        is ResponseException -> {
            msg
        }
        else -> {
            StringUtils.getString(R.string.string_throwable_message_unknown)
        }
    }
}