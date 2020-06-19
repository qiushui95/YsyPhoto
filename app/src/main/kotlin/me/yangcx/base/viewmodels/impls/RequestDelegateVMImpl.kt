package me.yangcx.base.viewmodels.impls

import android.os.Parcelable
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.distinctUntilChanged
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.*
import me.yangcx.base.viewmodels.delegates.RequestDelegateVM

class RequestDelegateVMImpl<DATA : Parcelable>(
    handle: SavedStateHandle,
    private val cancelBeforeRequest: Boolean,
    private val waitForBeforeFinish: Boolean,
    private val requestParentJob: Job = SupervisorJob(),
    keyPostfix: String = ""
) : RequestDelegateVM<DATA> {
    companion object {
        private const val KEY_BUSY_STATE = "RequestDelegateBusyState2"
        private const val KEY_ERROR = "RequestDelegateError2"
        private const val KEY_DATA = "RequestDelegateData2"
    }

    private val usedKeyPostfix by lazy {
        keyPostfix.takeIf {
            it.isNotBlank()
        } ?: System.identityHashCode(this).toString()
    }


    private val _busyStateLiveDelegate = lazy {
        handle.getLiveData<Boolean>(KEY_BUSY_STATE + usedKeyPostfix)
    }

    private val _busyStateLive by _busyStateLiveDelegate

    override val busyStateLive: LiveData<Boolean> by lazy {
        _busyStateLive.distinctUntilChanged()
    }

    private val _busyStateChannelDelegate = lazy {
        BroadcastChannel<Boolean>(1)
    }

    private val _busyStateChannel by _busyStateChannelDelegate

    override val busyStateFlow: Flow<Boolean> by lazy {
        _busyStateChannel.asFlow()
    }

    private val _errorLiveDelegate = lazy {
        handle.getLiveData<Throwable>(KEY_ERROR + usedKeyPostfix)
    }


    private val _errorLive by _errorLiveDelegate

    override val errorLive: LiveData<Throwable> by lazy {
        _errorLive
    }

    private val _errorChannelDelegate = lazy {
        BroadcastChannel<Throwable>(1)
    }

    private val _errorChannel by _errorChannelDelegate

    override val errorFlow: Flow<Throwable> by lazy {
        _errorChannel.asFlow()
    }

    private val _dataLiveDelegate = lazy {
        handle.getLiveData<DATA>(KEY_DATA + usedKeyPostfix)
    }

    private val _dataLive by _dataLiveDelegate

    override val dataLive: LiveData<DATA> by lazy {
        _dataLive.distinctUntilChanged()
    }

    private val _dataChannelDelegate = lazy {
        BroadcastChannel<DATA>(1)
    }

    private val _dataChannel by _dataChannelDelegate

    override val dataFlow: Flow<DATA> by lazy {
        _dataChannel.asFlow()
    }


    private var retryBlock: (CoroutineScope.() -> Unit)? = null

    @MainThread
    private fun setBusyState(busyState: Boolean) {
        if (_busyStateLiveDelegate.isInitialized()) {
            _busyStateLive.value = busyState
        }
        if (_busyStateChannelDelegate.isInitialized()) {
            _busyStateChannel.offer(busyState)
        }
    }

    @MainThread
    private fun setError(error: Throwable) {
        if (_errorLiveDelegate.isInitialized()) {
            _errorLive.value = error
        }
        if (_errorChannelDelegate.isInitialized()) {
            _errorChannel.offer(error)
        }
    }

    @MainThread
    private fun setData(data: DATA) {
        if (_dataLiveDelegate.isInitialized()) {
            _dataLive.value = data
        }
        if (_dataChannelDelegate.isInitialized()) {
            _dataChannel.offer(data)
        }
    }


    @MainThread
    @Synchronized
    override suspend fun doChangeBusyState(busyState: Boolean) {
        setBusyState(busyState)
    }

    @MainThread
    @Synchronized
    override suspend fun doChangeData(newData: DATA) {
        setData(newData)
    }

    @MainThread
    @Synchronized
    override suspend fun doChangeError(error: Throwable) {
        setError(error)
    }

    private suspend fun Flow<DATA>.dealFlowResult() {
        onStart {
            setBusyState(true)
        }.onCompletion {
            setBusyState(false)
        }.catch {
            setError(it)
            it.printStackTrace()
        }.flowOn(Dispatchers.Main)
            .collect {
                setData(it)
            }
    }

    private suspend fun beforeRequest() {
        if (cancelBeforeRequest) {
            requestParentJob.cancelChildren()
        }
        if (waitForBeforeFinish) {
            requestParentJob.children
                .forEach {
                    it.join()
                }
        }
    }

    override suspend fun retry() {
        coroutineScope {
            retryBlock?.invoke(this)
        }
    }

    override suspend fun doRequest(flowCreator: () -> Flow<DATA>) {
        retryBlock = {
            launch {
                beforeRequest()
                launch(Dispatchers.Main + requestParentJob) {
                    withContext(Dispatchers.IO) {
                        flowCreator()
                    }.flowOn(Dispatchers.IO)
                        .dealFlowResult()
                }
            }
        }
        coroutineScope {
            retryBlock?.invoke(this)
        }
    }
}