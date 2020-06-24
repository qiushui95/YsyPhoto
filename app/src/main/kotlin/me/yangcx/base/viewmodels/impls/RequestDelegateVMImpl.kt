package me.yangcx.base.viewmodels.impls

import android.os.Parcelable
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.distinctUntilChanged
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.*
import me.yangcx.base.entities.RequestStatus
import me.yangcx.base.parcelables.ParcelableList
import me.yangcx.base.viewmodels.delegates.RequestDelegateVM

typealias RequestListDelegateVMImpl<DATA> = RequestDelegateVMImpl<ParcelableList<DATA>>

class RequestDelegateVMImpl<DATA : Parcelable>(
    handle: SavedStateHandle?,
    private val cancelBeforeRequest: Boolean,
    private val waitForBeforeFinish: Boolean,
    private val requestParentJob: Job = SupervisorJob(),
    keyPostfix: String = ""
) : RequestDelegateVM<DATA> {
    companion object {
        private const val KEY_BUSY_STATE = "RequestDelegateBusyState2"
        private const val KEY_ERROR = "RequestDelegateError2"
        private const val KEY_DATA = "RequestDelegateData2"
        private const val KEY_REQUEST_STATUS = "RequestDelegateStatus2"
    }

    private val usedKeyPostfix by lazy {
        keyPostfix.takeIf {
            it.isNotBlank()
        } ?: System.identityHashCode(this).toString()
    }

    private val _busyStateLive by lazy {
        handle?.getLiveData<Boolean>(KEY_BUSY_STATE + usedKeyPostfix) ?: MutableLiveData()
    }

    override val busyStateLive: LiveData<Boolean> by lazy {
        _busyStateLive
    }

    override val busyStateLiveDistinct: LiveData<Boolean> by lazy {
        _busyStateLive.distinctUntilChanged()
    }

    private val _busyStateChannelDelegate = lazy {
        BroadcastChannel<Boolean>(1)
    }

    private val _busyStateChannel by _busyStateChannelDelegate

    override val busyStateFlow: Flow<Boolean> by lazy {
        _busyStateChannel.asFlow()
    }

    private val _errorLive by lazy {
        handle?.getLiveData<Throwable>(KEY_ERROR + usedKeyPostfix) ?: MutableLiveData()
    }

    override val errorLive: LiveData<Throwable> by lazy {
        _errorLive
    }

    override val errorLiveDistinct: LiveData<Throwable> by lazy {
        _errorLive.distinctUntilChanged()
    }

    private val _errorChannelDelegate = lazy {
        BroadcastChannel<Throwable>(1)
    }

    private val _errorChannel by _errorChannelDelegate

    override val errorFlow: Flow<Throwable> by lazy {
        _errorChannel.asFlow()
    }

    private val _dataLive by lazy {
        handle?.getLiveData<DATA>(KEY_DATA + usedKeyPostfix) ?: MutableLiveData()
    }

    override val dataLive: LiveData<DATA> by lazy {
        _dataLive
    }

    override val dataLiveDistinct: LiveData<DATA> by lazy {
        _dataLive.distinctUntilChanged()
    }

    private val _dataChannelDelegate = lazy {
        BroadcastChannel<DATA>(1)
    }

    private val _dataChannel by _dataChannelDelegate

    override val dataFlow: Flow<DATA> by lazy {
        _dataChannel.asFlow()
    }

    private val _requestStatusLive by lazy {
        handle?.getLiveData<RequestStatus>(KEY_REQUEST_STATUS + usedKeyPostfix) ?: MutableLiveData()
    }

    override val requestStatusLive: LiveData<RequestStatus> by lazy {
        _requestStatusLive
    }

    override val requestStatusLiveDistinct: LiveData<RequestStatus> by lazy {
        _requestStatusLive.distinctUntilChanged()
    }

    private val _requestStatusChannelDelegate = lazy {
        BroadcastChannel<RequestStatus>(1)
    }

    private val _requestStatusChannel by _requestStatusChannelDelegate

    override val requestStatusFlow: Flow<RequestStatus> by lazy {
        _requestStatusChannel.asFlow()
    }


    private var retryBlock: (CoroutineScope.() -> Unit)? = null

    @MainThread
    private fun setBusyState(busyState: Boolean) {
        _busyStateLive.value = busyState
        if (_busyStateChannelDelegate.isInitialized()) {
            _busyStateChannel.offer(busyState)
        }
        if (busyState) {
            setRequestStatus(RequestStatus.Loading)
        }
    }

    @MainThread
    private fun setError(error: Throwable) {
        _errorLive.value = error
        if (_errorChannelDelegate.isInitialized()) {
            _errorChannel.offer(error)
        }
        setRequestStatus(RequestStatus.Error(error))
    }

    @MainThread
    private fun setData(data: DATA) {
        _dataLive.value = data
        if (_dataChannelDelegate.isInitialized()) {
            _dataChannel.offer(data)
        }
        setRequestStatus(RequestStatus.Success(data))
    }

    @MainThread
    private fun setRequestStatus(requestStatus: RequestStatus) {
        _requestStatusLive.value = requestStatus
        if (_requestStatusChannelDelegate.isInitialized()) {
            _requestStatusChannel.offer(requestStatus)
        }
    }

    @MainThread
    @Synchronized
    override fun doChangeBusyState(busyState: Boolean) {
        setBusyState(busyState)
    }

    override fun getCurrentBusyState(): Boolean = _busyStateLive.value ?: false

    @MainThread
    @Synchronized
    override fun doChangeData(newData: DATA) {
        setData(newData)
    }

    override fun getCurrentData(): DATA? = _dataLive.value

    @MainThread
    @Synchronized
    override fun doChangeError(error: Throwable) {
        setError(error)
    }

    override fun getCurrentError(): Throwable? = _errorLive.value

    @MainThread
    @Synchronized
    override fun doChangeRequestStatus(requestStatus: RequestStatus) {
        setRequestStatus(requestStatus)
    }

    override fun getCurrentRequestStatus(): RequestStatus? = _requestStatusLive.value

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