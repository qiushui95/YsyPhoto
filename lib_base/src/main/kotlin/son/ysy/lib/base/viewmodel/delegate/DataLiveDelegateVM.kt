package son.ysy.lib.base.viewmodel.delegate

import android.os.Parcelable
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData

interface DataLiveDelegateVM<DATA : Parcelable> {

    val data: LiveData<DATA>

    val dataDistinct: LiveData<DATA>

    @MainThread
    fun changeData(newData: DATA)

    fun getCurrentData(): DATA?
}