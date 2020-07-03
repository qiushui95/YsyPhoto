package son.ysy.lib.base.viewmodel.impl

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import son.ysy.lib.base.viewmodel.delegate.RequestDelegateSimpleVM
import son.ysy.lib.base.viewmodel.delegate.RequestDelegateVM

typealias RequestListDelegateVMImplSimple<DATA> = RequestDelegateSimpleVMImpl<List<DATA>>

class RequestDelegateSimpleVMImpl<DATA : Parcelable>(
    handle: SavedStateHandle,
    private val coroutineScope: CoroutineScope,
    cancelCurrentIfBusy: Boolean = true,
    cancelBeforeRequest: Boolean = false,
    waitForBeforeFinish: Boolean = true,
    requestParentJob: Job = SupervisorJob(),
    keyPostfix: String = ""
) : RequestDelegateVM<DATA> by RequestDelegateVMImpl(
    handle,
    cancelCurrentIfBusy,
    cancelBeforeRequest,
    waitForBeforeFinish,
    requestParentJob,
    keyPostfix
), RequestDelegateSimpleVM<DATA> {

    override fun doRequestSimple(flowCreator: () -> Flow<DATA>) {
        doRequest(coroutineScope, flowCreator)
    }
}