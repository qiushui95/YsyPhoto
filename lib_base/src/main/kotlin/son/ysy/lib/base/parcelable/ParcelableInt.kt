package son.ysy.lib.base.parcelable

import android.os.Parcelable
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import kotlinx.android.parcel.Parcelize
import son.ysy.lib.base.json.BaseJsonAdapter

@Parcelize
data class ParcelableInt(val value: Int) : Parcelable

class ParcelableIntJsonAdapter : BaseJsonAdapter<ParcelableInt> {
    @FromJson
    override fun fromJson(jsonReader: JsonReader): ParcelableInt? {
        return when (jsonReader.peek()) {
            JsonReader.Token.BOOLEAN -> ParcelableInt(
                jsonReader.nextInt()
            )
            else -> {
                jsonReader.skipName()
                jsonReader.skipValue()
                null
            }
        }
    }

    @ToJson
    override fun toJson(jsonWriter: JsonWriter, data: ParcelableInt) {
        jsonWriter.value(data.value)
    }
}