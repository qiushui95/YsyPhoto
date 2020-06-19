package me.yangcx.base.viewmodels.impls

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import me.yangcx.base.viewmodels.delegates.RequestDelegateVM
import me.yangcx.base.viewmodels.delegates.RequestDelegateSimpleVM

typealias RequestListDelegateVMImplSimple<DATA> = RequestDelegateSimpleVMImpl<List<DATA>>

class RequestDelegateSimpleVMImpl<DATA : Parcelable>(
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
), RequestDelegateSimpleVM<DATA> {

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
}