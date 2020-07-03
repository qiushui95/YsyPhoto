package son.ysy.lib.base.ext

import android.os.Parcelable
import son.ysy.lib.base.parcelable.ParcelableList
import son.ysy.lib.base.parcelable.ParcelableString

fun String.parcelable() = ParcelableString(this)

inline fun <reified DATA : Parcelable> List<DATA>.parcelable() = ParcelableList(this)