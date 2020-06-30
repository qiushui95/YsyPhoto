package me.yangcx.base.viewmodels.delegates

import android.os.Parcelable
import kotlinx.coroutines.flow.Flow

interface RequestDelegateSimpleVM<DATA : Parcelable> : RequestDelegateVM<DATA> {
    fun doRequestSimple(flowCreator: () -> Flow<DATA>)
}