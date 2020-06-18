package son.ysy.photo.entities.response

import android.os.Parcelable
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class ResponseErrorInfo(
    @Json(name = "code")
    val code: Int,
    @Json(name = "message")
    val message: String
) : Parcelable