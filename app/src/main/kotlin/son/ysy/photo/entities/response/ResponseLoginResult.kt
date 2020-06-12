package son.ysy.photo.entities.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class ResponseLoginResult(
    @Json(name = "userId")
    val userId: String,
    @Json(name = "token")
    val token: String
) : Parcelable