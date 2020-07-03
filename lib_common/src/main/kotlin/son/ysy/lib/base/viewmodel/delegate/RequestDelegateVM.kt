package son.ysy.lib.base.viewmodel.delegate

import android.os.Parcelable
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import son.ysy.lib.base.entitiy.RequestStatus

interface RequestDelegateVM<DATA : Parcelable> {

    val busyStateLive: LiveData<Boolean>

    val busyStateLiveDistinct: LiveData<Boolean>

    val busyStateFlow: Flow<Boolean>

    val errorLive: LiveData<Throwable>

    val errorLiveDistinct: LiveData<Throwable>

    val errorFlow: Flow<Throwable>

    val dataLive: LiveData<DATA>

    val dataLiveDistinct: LiveData<DATA>

    val dataFlow: Flow<DATA>

    val requestStatusLive: LiveData<RequestStatus>

    val requestStatusLiveDistinct: LiveData<RequestStatus>

    val requestStatusFlow: Flow<RequestStatus>

    fun doChangeBusyState(busyState: Boolean)

    fun getCurrentBusyState(): Boolean

    fun doChangeError(error: Throwable)

    fun getCurrentError(): Throwable?

    fun doChangeData(newData: DATA)

    fun getCurrentData(): DATA?

    fun doChangeRequestStatus(requestStatus: RequestStatus)

    fun getCurrentRequestStatus(): RequestStatus?

    fun retry()

    fun doRequest(coroutineScope: CoroutineScope, flowCreator: () -> Flow<DATA>)
}