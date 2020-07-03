package son.ysy.lib.base.parcelable

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ParcelableInt(val value: Int) : Parcelable {
    override fun toString(): String {
        return value.toString()
    }
}