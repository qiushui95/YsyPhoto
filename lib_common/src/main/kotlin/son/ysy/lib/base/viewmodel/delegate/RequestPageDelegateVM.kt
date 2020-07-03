package son.ysy.lib.base.viewmodel.delegate

import android.os.Parcelable
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

interface RequestPageDelegateVM<DATA : Parcelable> {
    val busyStateLive: LiveData<Boolean>

    val busyStateFlow: Flow<Boolean>

    val errorLive: LiveData<Throwable>

    val errorFlow: Flow<Throwable>

    val dataLive: LiveData<DATA>

    val dataFlow: Flow<DATA>

    suspend fun doChangeBusyState(busyState: Boolean)

    suspend fun doChangeError(error: Throwable)

    suspend fun doChangeData(newData: DATA)

    suspend fun retry()

    suspend fun doRequest(isRefresh: Boolean, flowCreator: () -> Flow<DATA>)
}