package com.example.simpletodoapp.model

/*
 * データクラス
 * カードの中身のテーブル定義
 */
data class TodoUiModel(
    val id: Long,
    val title: String,
    val description: String,
    val isDone: Boolean,
)
