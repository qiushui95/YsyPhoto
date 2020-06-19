package son.ysy.photo.exceptions

import me.yangcx.base.exceptions.MessageThrowable

open class ResponseException(val code: Int, val msg: String) : Throwable(msg), MessageThrowable