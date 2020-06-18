package son.ysy.photo.entities.response

import android.os.Parcelable
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class ResponseUserInfo(
    @Json(name = "avatarUrl")
    val avatarUrl: String,
    @Json(name = "phone")
    val phone: String,
    @Json(name = "relationShip")
    val relationShip: String
) : Parcelable