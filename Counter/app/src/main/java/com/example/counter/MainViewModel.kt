package com.example.counter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    // 画面の単一の状態源。学習用として Compose で分かりやすい mutableStateOf を使う
    var count by mutableIntStateOf(0)
        // byはmutableIntStateOf(0) が持っている値の取り出し・更新を、count という変数として使えるようにする
        // count は外から読めるけど、外から変更はできない、という意味
        private set

    fun increment() {
        count += 1
    }

    fun decrement() {
        if (count > 0) {
            count -= 1
        }
    }
}
