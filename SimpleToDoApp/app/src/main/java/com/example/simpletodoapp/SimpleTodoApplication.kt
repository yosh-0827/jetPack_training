package com.example.simpletodoapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Hilt の起点になる Application クラス。
 *
 * @HiltAndroidApp を付けることで、
 * このアプリ全体で Hilt が使えるようになる。
 *
 * ここがあることで、後から Activity / ViewModel / Module の依存関係を
 * Hilt がまとめて解決できるようになる。
 */
@HiltAndroidApp
class SimpleTodoApplication : Application()
