package me.yangcx.base.viewmodels.delegates

import android.os.Parcelable
import kotlinx.coroutines.flow.Flow

interface RequestDelegateSimpleVM<DATA : Parcelable> : RequestDelegateVM<DATA> {
    fun doChangeBusyStateSimple(busyState: Boolean)
    fun doChangeErrorSimple(error: Throwable)
    fun doChangeDataSimple(newData: DATA)
    fun retrySimple()
    fun doRequestSimple(flowCreator: () -> Flow<DATA>)
    fun doRequestSimple(flowCreator: () -> Flow<DATA>, outDeal: (flow: Flow<DATA>) -> Flow<DATA>)
    fun <RESPONSE : Any> doMapperRequestSimple(
        flowCreator: () -> Flow<RESPONSE>,
        flowMapper: (mapper: RESPONSE) -> DATA?
    )

    fun <RESPONSE : Any> doMapperRequestSimple(
        flowCreator: () -> Flow<RESPONSE>,
        flowMapper: (mapper: RESPONSE) -> DATA?,
        outDeal: (flow: Flow<DATA>) -> Flow<DATA>
    )
}