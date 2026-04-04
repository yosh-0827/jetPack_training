package com.example.simpletodoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simpletodoapp.components.TodoItemCard
import com.example.simpletodoapp.components.TodoListScreen
import com.example.simpletodoapp.model.TodoUiModel
import com.example.simpletodoapp.model.sampleTodoItems
import com.example.simpletodoapp.ui.theme.SimpleTodoAppTheme

class MainActivity : ComponentActivity() {
    // 画面が作られるときに呼ばれる関数
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 画面を端まで広く使いやすくする設定
        enableEdgeToEdge()
        // ここから先は Compose でUIを作る、という意味
        setContent {
            // アプリの色や見た目のルールを適用しています

            val todoListViewModel: TodoListViewModel = viewModel()
            SimpleTodoAppTheme {
                // 一覧画面を表示
                TodoListScreen(
                    todoItems = todoListViewModel.todoItems,
                    onAddClick = {},
                    onTodoClick = {},
                    onCheckedChange = {targetTodo, isChecked ->
                        todoListViewModel.onCheckedChange(targetTodo, isChecked)
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TodoListScreenPreview() {
    SimpleTodoAppTheme {
        TodoListScreen(
            todoItems = sampleTodoItems,
            onAddClick = {},
            onTodoClick = {},
            onCheckedChange = { _, _ -> }
        )
    }
}
