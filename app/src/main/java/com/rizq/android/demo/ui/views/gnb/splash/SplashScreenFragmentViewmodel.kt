package com.rizq.android.demo.ui.views.gnb.splash

import androidx.lifecycle.*
import androidx.navigation.findNavController
import com.rizq.android.demo.ui.views.gnb.productselection.ProductSelectionGNBFragmentScreenState
import com.rizq.android.domain.core.ScreenState
import com.rizq.android.usecases.gnb.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SplashScreenFragmentViewmodel @Inject constructor(): ViewModel() {

  private val _state = MutableLiveData<ScreenState<SplashScreenFragmentScreenState>>()
  val state: LiveData<ScreenState<SplashScreenFragmentScreenState>> get() = _state

  fun initiateTimer(){
    Timer().schedule(object : TimerTask() {
      override fun run() {
        // Basic SplashScreen to show case Jetpack Navigation
        viewModelScope.launch {
          _state.value = (ScreenState.Render(SplashScreenFragmentScreenState.StartNavigation))
        }
      }
    }, 2000)
  }

}