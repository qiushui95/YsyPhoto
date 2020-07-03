package son.ysy.lib.base.json

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson

interface BaseJsonAdapter<T : Any> {

    @FromJson
    fun fromJson(jsonReader: JsonReader): T?

    @ToJson
    fun toJson(jsonWriter: JsonWriter, data: T)
}