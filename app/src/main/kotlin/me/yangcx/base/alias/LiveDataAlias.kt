package me.yangcx.base.alias

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

typealias MutableLiveListData<DATA> = MutableLiveData<List<DATA>>

typealias LiveListData<DATA> = LiveData<List<DATA>>