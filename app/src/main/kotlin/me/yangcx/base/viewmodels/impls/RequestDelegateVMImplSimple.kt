package me.yangcx.base.viewmodels.impls

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import me.yangcx.base.viewmodels.delegates.RequestDelegateVM
import me.yangcx.base.viewmodels.delegates.RequestDelegateVMSimple

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
), RequestDelegateVMSimple<DATA> {

    override fun doChangeBusyStateSimple(busyState: Boolean) {
        coroutineScope.launch(Dispatchers.Main) {
            doChangeBusyState(busyState)
        }
    }

    override fun doChangeErrorSimple(error: Throwable) {
        coroutineScope.launch(Dispatchers.Main) {
            doChangeError(error)
        }
    }

    override fun doChangeDataSimple(newData: DATA) {
        coroutineScope.launch(Dispatchers.Main) {
            doChangeData(newData)
        }
    }

    override fun retrySimple() {
        coroutineScope.launch(Dispatchers.Main) {
            retry()
        }
    }

    override fun doRequestSimple(flowCreator: () -> Flow<DATA>) {
        coroutineScope.launch(Dispatchers.Main) {
            doRequest(flowCreator)
        }
    }

    override fun doRequestSimple(
        flowCreator: () -> Flow<DATA>,
        outDeal: (flow: Flow<DATA>) -> Flow<DATA>
    ) {
        coroutineScope.launch(Dispatchers.Main) {
            doRequest(flowCreator, outDeal)
        }
    }

    override fun <RESPONSE : Any> doMapperRequestSimple(
        flowCreator: () -> Flow<RESPONSE>,
        flowMapper: (mapper: RESPONSE) -> DATA?
    ) {
        coroutineScope.launch(Dispatchers.Main) {
            doMapperRequest(flowCreator, flowMapper)
        }
    }

    override fun <RESPONSE : Any> doMapperRequestSimple(
        flowCreator: () -> Flow<RESPONSE>,
        flowMapper: (mapper: RESPONSE) -> DATA?,
        outDeal: (flow: Flow<DATA>) -> Flow<DATA>
    ) {
        coroutineScope.launch(Dispatchers.Main) {
            doMapperRequest(flowCreator, flowMapper, outDeal)
        }
    }
}