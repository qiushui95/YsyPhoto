package me.yangcx.base.ext

import android.os.Looper
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe

inline fun <reified T : Any> MutableLiveData<T>.updateValue(
    updateWithOldDataBloc: (oldData: T?) -> T?
): T? = updateWithOldDataBloc(value).also { newValue ->
    if (Looper.getMainLooper().thread == Thread.currentThread()) {
        value = newValue
    } else {
        postValue(newValue)
    }
}

fun <T1 : Any, T2 : Any> LiveData<T1>.merge(
    mergeLiveData: LiveData<T2>,
    lifecycleOwner: LifecycleOwner,
    onChangeBlock: (data1: T1?, data2: T2?) -> Unit
) {
    observe(lifecycleOwner) {
        onChangeBlock(it, mergeLiveData.value)
    }
    mergeLiveData.observe(lifecycleOwner) {
        onChangeBlock(value, it)
    }
}

fun <T0 : Any, T1 : Any, T2 : Any> LiveData<T0>.merge(
    mergeLiveData1: LiveData<T1>,
    mergeLiveData2: LiveData<T2>,
    lifecycleOwner: LifecycleOwner,
    onChangeBlock: (data1: T0?, data2: T1?, data3: T2?) -> Unit
) {
    observe(lifecycleOwner) {
        onChangeBlock(it, mergeLiveData1.value, mergeLiveData2.value)
    }
    mergeLiveData1.observe(lifecycleOwner) {
        onChangeBlock(value, it, mergeLiveData2.value)
    }
    mergeLiveData2.observe(lifecycleOwner) {
        onChangeBlock(value, mergeLiveData1.value, it)
    }
}