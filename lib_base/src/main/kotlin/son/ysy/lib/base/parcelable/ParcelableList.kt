package son.ysy.lib.base.parcelable

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ParcelableList<DATA : Parcelable>(val value: List<DATA>) : Parcelable {
    override fun toString(): String {
        return value.toString()
    }
}