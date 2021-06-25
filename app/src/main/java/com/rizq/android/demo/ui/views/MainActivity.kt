package com.rizq.android.demo.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rizq.android.demo.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }
}