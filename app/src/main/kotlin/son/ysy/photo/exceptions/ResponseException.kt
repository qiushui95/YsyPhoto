package son.ysy.photo.exceptions

class ResponseException(val code: Int, val msg: String) : Throwable(msg)