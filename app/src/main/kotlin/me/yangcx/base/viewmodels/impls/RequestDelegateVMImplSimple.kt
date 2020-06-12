package me.yangcx.base.viewmodels.impls

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import me.yangcx.base.viewmodels.delegates.RequestDelegateVM

typealias RequestListDelegateVMImplSimple<DATA> = RequestDelegateVMImplSimple<List<DATA>>

class RequestDelegateVMImplSimple<DATA : Parcelable>(
    handle: SavedStateHandle,
    private val coroutineScope: CoroutineScope,
    cancelBeforeRequest: Boolean,
    waitForBeforeFinish: Boolean,
    requestParentJob: Job = SupervisorJob(),
    keyPostfix: String = ""
) : RequestDelegateVM<DATA> by RequestDelegateVMImpl(
    handle,
    cancelBeforeRequest,
    waitForBeforeFinish,
    requestParentJob,
    keyPostfix
) {
    fun doChangeBusyStateSimple(busyState: Boolean) {
        coroutineScope.launch(Dispatchers.Main) {
            doChangeBusyState(busyState)
        }
    }

    fun doChangeErrorSimple(error: Throwable) {
        coroutineScope.launch(Dispatchers.Main) {
            doChangeError(error)
        }
    }

    fun doChangeDataSimple(newData: DATA) {
        coroutineScope.launch(Dispatchers.Main) {
            doChangeData(newData)
        }
    }

    fun retrySimple() {
        coroutineScope.launch(Dispatchers.Main) {
            retry()
        }
    }

    fun doRequestSimple(flowCreator: () -> Flow<DATA>) {
        coroutineScope.launch(Dispatchers.Main) {
            doRequest(flowCreator)
        }
    }

    fun doRequestSimple(
        flowCreator: () -> Flow<DATA>,
        outDeal: (flow: Flow<DATA>) -> Flow<DATA>
    ) {
        coroutineScope.launch(Dispatchers.Main) {
            doRequest(flowCreator, outDeal)
        }
    }

    fun <RESPONSE : Any> doMapperRequestSimple(
        flowCreator: () -> Flow<RESPONSE>,
        flowMapper: (mapper: RESPONSE) -> DATA?
    ) {
        coroutineScope.launch(Dispatchers.Main) {
            doMapperRequest(flowCreator, flowMapper)
        }
    }

    fun <RESPONSE : Any> doMapperRequestSimple(
        flowCreator: () -> Flow<RESPONSE>,
        flowMapper: (mapper: RESPONSE) -> DATA?,
        outDeal: (flow: Flow<DATA>) -> Flow<DATA>
    ) {
        coroutineScope.launch(Dispatchers.Main) {
            doMapperRequest(flowCreator, flowMapper, outDeal)
        }
    }
}