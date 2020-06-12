package me.yangcx.base.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

abstract class BaseViewModel : ViewModel(), LifecycleObserver,KoinComponent {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun onCreate() {
        viewModelScope.launch {
            runOnCreate()
        }
    }

    protected open suspend fun runOnCreate() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        viewModelScope.launch {
            runOnStart()
        }
    }

    protected open suspend fun runOnStart() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onResume() {
        viewModelScope.launch {
            runOnResume()
        }
    }

    protected open suspend fun runOnResume() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun onPause() {
        viewModelScope.launch {
            runOnPause()
        }
    }

    protected open suspend fun runOnPause() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        viewModelScope.launch {
            runOnStop()
        }
    }


    protected open suspend fun runOnStop() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroy() {
        viewModelScope.launch {
            runOnDestroy()
        }
    }

    protected open suspend fun runOnDestroy() {

    }
}