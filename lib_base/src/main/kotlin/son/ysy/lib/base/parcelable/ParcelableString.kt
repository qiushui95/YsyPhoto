package son.ysy.lib.base.parcelable

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ParcelableString(val value: String) : Parcelable {
    override fun toString(): String {
        return value
    }
}