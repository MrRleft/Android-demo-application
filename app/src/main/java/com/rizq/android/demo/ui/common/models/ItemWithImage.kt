package com.rizq.android.demo.ui.common.models

import androidx.core.graphics.drawable.RoundedBitmapDrawable
import com.rizq.android.domain.models.local.FutureObjectWithImage

data class ItemWithImage(val objectWithoutImage: FutureObjectWithImage, val bitmap: RoundedBitmapDrawable)