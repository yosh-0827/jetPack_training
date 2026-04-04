package com.example.simpletodoapp.model

/*
 * データクラス
 * カードの中身のテーブル定義(Entityを作るまでの繋ぎ)
 */
data class TodoUiModel(
    val id: Long,
    val title: String,
    val description: String,
    val isDone: Boolean,
)
