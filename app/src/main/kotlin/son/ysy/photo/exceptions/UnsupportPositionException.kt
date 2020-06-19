package son.ysy.photo.exceptions

import me.yangcx.base.exceptions.MessageThrowable

class UnsupportPositionException(
    position: Int
) : RuntimeException("unsupport position $position!!"), MessageThrowable