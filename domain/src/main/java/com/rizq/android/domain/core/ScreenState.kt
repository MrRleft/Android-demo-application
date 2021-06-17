package com.rizq.android.domain.core

sealed class ScreenState<out T>
{
    object Loading: ScreenState<Nothing>()
    class Render<T>(val renderState: T) : ScreenState<T>()
}
