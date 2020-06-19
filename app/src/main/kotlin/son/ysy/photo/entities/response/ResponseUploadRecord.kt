package son.ysy.photo.entities.response

import android.os.Parcelable
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
data class ResponseUploadRecord(
    @Json(name = "id")
    val id: String,
    @Json(name = "imageUrl")
    val imageUrl: String,
    @Json(name = "width")
    val width: Int,
    @Json(name = "height")
    val height: Int,
    @Json(name = "publisher")
    val publisher: String,
    @Json(name = "takeTime")
    val takeTime: Int,
    @Json(name = "uploadTime")
    val uploadTime: Int
) : Parcelable