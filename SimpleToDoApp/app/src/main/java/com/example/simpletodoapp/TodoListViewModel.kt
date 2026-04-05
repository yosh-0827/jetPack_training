package com.example.simpletodoapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletodoapp.data.TodoEntity
import com.example.simpletodoapp.data.TodoRepository
import com.example.simpletodoapp.model.TodoUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 一覧画面の状態を持つ ViewModel。
 *
 * 役割は、
 * - Repository から Todo 一覧を受け取る
 * - 画面表示用の TodoUiModel に変換する
 * - チェック変更や新規追加の操作を Repository に依頼する
 *
 * UI は「どう表示するか」に集中し、
 * データ更新の責務は ViewModel 側に寄せている。
 */
@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository,
) : ViewModel() {
    var todoItems by mutableStateOf<List<TodoUiModel>>(emptyList())
        private set

    init {
        viewModelScope.launch {
            // Room の Flow は DB 変更時に新しい一覧を流してくる。
            // collect でそれを受け取り、画面表示用の状態へ変換している。
            repository.getAllTodos().collect { entities ->
                todoItems = entities.map { entity ->
                    TodoUiModel(
                        id = entity.id,
                        title = entity.title,
                        description = entity.description,
                        isDone = entity.isDone
                    )
                }
            }
        }
    }

    /**
     * チェック状態が変わったときの更新処理。
     *
     * 直接 todoItems を書き換えるのではなく、まず DB を更新する。
     * すると Room の Flow が新しい一覧を流し、それを init 内の collect が受け取る。
     */
    fun onCheckedChange(targetTodo: TodoUiModel, isChecked: Boolean) {
        updateTodo(
            todoId = targetTodo.id,
            title = targetTodo.title,
            description = targetTodo.description,
            isDone = isChecked
        )
    }

    /**
     * 新規 Todo の追加処理。
     *
     * 新規作成画面から受け取った文字列を Entity に変換して DB へ保存する。
     * trim() しているのは、前後の空白だけで保存されるのを少し防ぐため。
     */
    fun addTodo(title: String, description: String) {
        viewModelScope.launch {
            repository.insert(
                TodoEntity(
                    title = title.trim(),
                    description = description.trim(),
                    isDone = false
                )
            )
        }
    }

    /**
     * route で渡された id から、現在の一覧の中の1件を探す。
     */
    fun findTodoById(todoId: Long): TodoUiModel? {
        return todoItems.firstOrNull { todo ->
            todo.id == todoId
        }
    }

    /**
     * 詳細画面からの更新処理。
     */
    fun updateTodo(
        todoId: Long,
        title: String,
        description: String,
        isDone: Boolean,
    ) {
        viewModelScope.launch {
            repository.update(
                TodoEntity(
                    id = todoId,
                    title = title.trim(),
                    description = description.trim(),
                    isDone = isDone
                )
            )
        }
    }

    /**
     * 詳細画面からの削除処理。
     */
    fun deleteTodo(todoId: Long) {
        val targetTodo = findTodoById(todoId) ?: return

        viewModelScope.launch {
            repository.delete(
                TodoEntity(
                    id = targetTodo.id,
                    title = targetTodo.title,
                    description = targetTodo.description,
                    isDone = targetTodo.isDone
                )
            )
        }
    }
}
