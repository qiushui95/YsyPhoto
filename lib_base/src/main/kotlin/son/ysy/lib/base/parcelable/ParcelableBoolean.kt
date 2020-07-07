package son.ysy.lib.base.parcelable

import android.os.Parcelable
import com.squareup.moshi.*
import kotlinx.android.parcel.Parcelize
import son.ysy.lib.base.json.BaseJsonAdapter

@Parcelize
data class ParcelableBoolean(val value: Boolean) : Parcelable {
    override fun toString(): String {
        return value.toString()
    }
}

class ParcelableBooleanJsonAdapter : BaseJsonAdapter<ParcelableBoolean> {
    @FromJson
    override fun fromJson(jsonReader: JsonReader): ParcelableBoolean? {
        return when (jsonReader.peek()) {
            JsonReader.Token.BOOLEAN -> ParcelableBoolean(
                jsonReader.nextBoolean()
            )
            else -> {
                jsonReader.skipName()
                jsonReader.skipValue()
                null
            }
        }
    }

    @ToJson
    override fun toJson(jsonWriter: JsonWriter, data: ParcelableBoolean) {
        jsonWriter.value(data.value)
    }
}