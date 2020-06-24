package son.ysy.photo.entities.ui

import android.net.Uri
import android.os.Parcelable
import androidx.core.net.toUri
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageInfo(
    val id: String,
    val fileName: String,
    val width: Int,
    val height: Int,
    val size: Int
) : Parcelable {
    @IgnoredOnParcel
    val uri: Uri = Uri.withAppendedPath("content://media/external/images/media".toUri(), id)
}