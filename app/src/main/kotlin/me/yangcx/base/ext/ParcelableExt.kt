package me.yangcx.base.ext

import android.os.Parcelable
import me.yangcx.base.parcelables.ParcelableList
import me.yangcx.base.parcelables.ParcelableString

fun String.parcelable() = ParcelableString(this)

inline fun <reified DATA : Parcelable> List<DATA>.parcelable() = ParcelableList(this)