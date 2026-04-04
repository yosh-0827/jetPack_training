package com.example.simpletodoapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.simpletodoapp.model.TodoUiModel
import com.example.simpletodoapp.model.sampleTodoItems

class TodoListViewModel: ViewModel() {
    // TODOアイテム一覧の状態
    var todoItems by mutableStateOf(sampleTodoItems)
        private set // 外から直接書き換えられないようにする.更新は onCheckedChange() 経由にする

    fun onCheckedChange(targetTodo: TodoUiModel, isChecked: Boolean) {
        todoItems = todoItems.map {
            todo ->
            if (todo.id == targetTodo.id) {
                todo.copy(isDone = isChecked)
            } else {
                todo
            }
        }
    }
}