package son.ysy.photo.data

import android.os.Parcelable
import com.tencent.mmkv.MMKV
import son.ysy.photo.entities.response.ResponseLoginResult

object SavedKVData {
    private const val KEY_LOGIN_RESULT = "loginResult"

    private val mmkv = MMKV.defaultMMKV(MMKV.SINGLE_PROCESS_MODE, "ysy")

    private inline fun <reified DATA : Parcelable> MMKV.decodeParcelable(
        key: String
    ) = decodeParcelable(key, DATA::class.java)

    var loginResult: ResponseLoginResult?
        get() = mmkv.decodeParcelable(KEY_LOGIN_RESULT)
        set(value) {
            if (value == null) {
                mmkv.remove(KEY_LOGIN_RESULT)
            } else {
                mmkv.encode(KEY_LOGIN_RESULT, value)
            }
        }
}