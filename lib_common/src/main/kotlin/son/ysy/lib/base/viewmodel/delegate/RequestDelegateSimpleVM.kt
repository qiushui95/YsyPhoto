package son.ysy.lib.base.viewmodel.delegate

import android.os.Parcelable
import kotlinx.coroutines.flow.Flow

interface RequestDelegateSimpleVM<DATA : Parcelable> : RequestDelegateVM<DATA> {
    fun doRequestSimple(flowCreator: () -> Flow<DATA>)
}