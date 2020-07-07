package son.ysy.lib.base.parcelable

import android.os.Parcelable
import com.squareup.moshi.*
import kotlinx.android.parcel.Parcelize
import son.ysy.lib.base.ext.parcelable
import son.ysy.lib.base.json.BaseJsonAdapter

@Parcelize
data class ParcelableList<DATA : Parcelable>(val value: List<DATA>) : Parcelable


class ParcelableListJsonAdapter<DATA : Parcelable>(
    moShi: Moshi,
    clz: Class<DATA>
) : BaseJsonAdapter<ParcelableList<DATA>> {
    private val dataAdapter = moShi.adapter<List<DATA>>(
        Types.newParameterizedType(List::class.java, clz)
    )

    @FromJson
    override fun fromJson(jsonReader: JsonReader): ParcelableList<DATA>? {
        return dataAdapter.fromJson(jsonReader)?.run {
            ParcelableList(this)
        }
    }

    @ToJson
    override fun toJson(jsonWriter: JsonWriter, data: ParcelableList<DATA>) {
        dataAdapter.toJson(jsonWriter, data.value)
    }
}