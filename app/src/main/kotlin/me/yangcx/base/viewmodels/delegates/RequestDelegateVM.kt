package me.yangcx.base.viewmodels.delegates

import android.os.Parcelable
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

interface RequestDelegateVM<DATA : Parcelable> {

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

    suspend fun doRequest(flowCreator: () -> Flow<DATA>)

    suspend fun doRequest(
        flowCreator: () -> Flow<DATA>,
        outDeal: (flow: Flow<DATA>) -> Flow<DATA>
    )

    suspend fun <RESPONSE : Any> doMapperRequest(
        flowCreator: () -> Flow<RESPONSE>,
        flowMapper: (mapper: RESPONSE) -> DATA?
    )

    suspend fun <RESPONSE : Any> doMapperRequest(
        flowCreator: () -> Flow<RESPONSE>,
        flowMapper: (mapper: RESPONSE) -> DATA?,
        outDeal: (flow: Flow<DATA>) -> Flow<DATA>
    )
}