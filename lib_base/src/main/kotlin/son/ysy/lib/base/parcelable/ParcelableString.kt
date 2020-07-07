package son.ysy.lib.base.parcelable

import android.os.Parcelable
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import kotlinx.android.parcel.Parcelize
import son.ysy.lib.base.json.BaseJsonAdapter

@Parcelize
data class ParcelableString(val value: String) : Parcelable


class ParcelableStringJsonAdapter : BaseJsonAdapter<ParcelableString> {
    @FromJson
    override fun fromJson(jsonReader: JsonReader): ParcelableString? {
        return when (jsonReader.peek()) {
            JsonReader.Token.STRING -> ParcelableString(
                jsonReader.nextString()
            )
            else -> {
                jsonReader.skipName()
                jsonReader.skipValue()
                null
            }
        }
    }

    @ToJson
    override fun toJson(jsonWriter: JsonWriter, data: ParcelableString) {
        jsonWriter.value(data.value)
    }
}