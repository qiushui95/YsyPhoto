package son.ysy.photo.entities.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class ResponseTokenInfo(
    @Json(name = "token")
    val token: String,
    @Json(name = "prefix")
    val prefix: String
) : Parcelable