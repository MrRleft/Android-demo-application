package com.rizq.android.demo.framework.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizq.android.domain.core.*

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

/**
 * Rubén Izquierdo, Global Incubator
 * */

abstract class BaseViewModel: ViewModel()
{
    protected var _failure: MutableLiveData<Failure> = MutableLiveData()

    val failure: LiveData<Failure>
        get() = _failure

    protected fun handleFailure(failure: Failure) {
        this._failure.value = failure
    }

    open var _state: MutableLiveData<ScreenState<ScreenStateImplementation>> = MutableLiveData()

    val state: LiveData<ScreenState<ScreenStateImplementation>>
        get() = _state

    protected fun handleState(state: ScreenStateImplementation) {
        this._state.value = ScreenState.Render(state)
    }

    protected fun startLoadingState(){
        this._state.value = ScreenState.Loading
    }
}