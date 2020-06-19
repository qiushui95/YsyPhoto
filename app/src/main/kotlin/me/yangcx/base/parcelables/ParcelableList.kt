package me.yangcx.base.parcelables

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ParcelableList<DATA : Parcelable>(val value: List<DATA>) : Parcelable {
    override fun toString(): String {
        return value.toString()
    }
}