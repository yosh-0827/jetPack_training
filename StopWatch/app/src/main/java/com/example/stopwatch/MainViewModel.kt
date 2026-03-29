package com.example.stopwatch

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    // varは変更可能な変数, valは変更不可能な変数。final的な。
    private val initialSeconds = 30
    var remainingSeconds by mutableStateOf(initialSeconds)
        private set

    // 動いてるかどうか
    var isRunning by mutableStateOf(false)
        private set

    // 今動いているタイマー処理をあとで止められるように、処理そのものを変数に保存しておく
    // Job は、コルーチンで動いている処理そのものを表す管理用オブジェクト
    private var timerJob: Job? = null

    /*
    start() は「まだ動いていなければ、1秒ごとに残り時間を減らす仕事を開始する関数」です。
    理解のコツはこの対応です。
      remainingSeconds: 今あと何秒か
      isRunning: 今動いているか
      timerJob: 動いているタイマー処理そのもの
     */
    fun start() {
        // すでに動いているなら何もしない
        if (isRunning) return

        isRunning = true
        // viewModelScope.launch { ... } でコルーチンを開始すると、戻り値として Job が返ってきます。
        timerJob = viewModelScope.launch {
            while (isRunning && remainingSeconds > 0) {
                delay(1000) // 1秒待つ
                remainingSeconds-- // 残り秒数を1減らす
            }
        }
    }

    fun pause() {
        isRunning = false
        timerJob?.cancel()
    }

    fun reset() {
        isRunning = false
        timerJob?.cancel()
        remainingSeconds = initialSeconds
    }
}