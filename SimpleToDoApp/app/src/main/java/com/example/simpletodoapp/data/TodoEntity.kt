package com.example.simpletodoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * DBテーブルに相当
 * */
@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)  // 新規作成時にIDを自動採番
    val id: Long = 0L,
    val title: String,
    val description: String,
    val isDone: Boolean,
)