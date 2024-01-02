package com.rizq.android.demo.ui.views.gnb.splash

import android.os.*
import android.view.View
import androidx.fragment.app.*
import androidx.navigation.findNavController
import com.rizq.android.demo.R
import com.rizq.android.demo.databinding.*
import com.rizq.android.demo.ui.views.gnb.productselection.ProductSelectionGNBFragmentScreenState
import com.rizq.android.domain.core.ScreenState
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SplashScreenFragment : Fragment(R.layout.fragment_splash_screen) {

  private lateinit var binding: FragmentSplashScreenBinding
  private val mViewModel: SplashScreenFragmentViewmodel by viewModels()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding = FragmentSplashScreenBinding.inflate(layoutInflater)
    initStates()
    mViewModel.initiateTimer()
  }

  private fun initStates() {
    mViewModel.state.observe(viewLifecycleOwner, ::updateUI)
  }

  private fun updateUI(screenState: ScreenState<SplashScreenFragmentScreenState>) {
    when (screenState) {
      ScreenState.Loading -> {
      }
      is ScreenState.Render -> renderScreenState(screenState.renderState)
      else -> {
      }
    }
  }

  private fun renderScreenState(renderState: SplashScreenFragmentScreenState) {
    when (renderState) {
      SplashScreenFragmentScreenState.StartNavigation -> {
        val action = SplashScreenFragmentDirections.actionSplashScreenFragmentToProductSelectionGNBFragment()
        view?.findNavController()?.navigate(action)
      }
    }
  }
}