package com.example.goodbuttoncounter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var goodCount by mutableIntStateOf(0)
        private set

    var badCount by mutableIntStateOf(0)
        private set

    fun onGoodClick() {
        goodCount++
    }

    fun onBadClick() {
        badCount++
    }

    fun onResetClick() {
        goodCount = 0
        badCount = 0
    }
}
