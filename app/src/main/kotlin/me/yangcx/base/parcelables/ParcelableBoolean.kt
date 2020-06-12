package me.yangcx.base.parcelables

import android.os.Parcelable
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import kotlinx.android.parcel.Parcelize
import me.yangcx.base.json.BaseJsonAdapter

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
            JsonReader.Token.BOOLEAN -> ParcelableBoolean(jsonReader.nextBoolean())
            else -> {
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