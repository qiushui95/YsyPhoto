package me.yangcx.base.viewmodels.impls

import android.os.Parcelable
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.distinctUntilChanged
import me.yangcx.base.parcelables.ParcelableList
import me.yangcx.base.viewmodels.delegates.DataLiveDelegateVM

typealias DataListLiveDelegateVMImpl<DATA> = DataLiveDelegateVMImpl<ParcelableList<DATA>>

class DataLiveDelegateVMImpl<DATA : Parcelable>(
    handle: SavedStateHandle,
    initialValue: DATA? = null,
    keyPostfix: String = ""
) : DataLiveDelegateVM<DATA> {

    private val usedKeyPostfix = keyPostfix.takeIf {
        it.isNotBlank()
    } ?: System.identityHashCode(this).toString()

    private val _data by lazy {
        if (initialValue == null) {
            handle.getLiveData<DATA>(usedKeyPostfix)
        } else {
            handle.getLiveData<DATA>(usedKeyPostfix, initialValue)
        }
    }

    override val data: LiveData<DATA> by lazy {
        _data
    }

    override val dataDistinct: LiveData<DATA> by lazy {
        _data.distinctUntilChanged()
    }

    @MainThread
    override fun changeData(newData: DATA) {
        _data.value = newData
    }

    override fun getCurrentData(): DATA? = _data.value
}