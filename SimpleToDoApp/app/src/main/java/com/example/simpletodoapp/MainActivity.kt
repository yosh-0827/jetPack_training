package com.example.simpletodoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simpletodoapp.components.TodoListScreen
import com.example.simpletodoapp.model.sampleTodoItems
import com.example.simpletodoapp.ui.theme.SimpleTodoAppTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Android アプリの起動入口。
 *
 * いまの役割はかなり絞っていて、
 * - Hilt が生成してくれる ViewModel を取得する
 * - Compose のルート画面(AppNavHost)を表示する
 *
 * Room の Database / Dao / Repository は MainActivity ではもう作らず、
 * Hilt の Module に任せている。
 *
 * 実際の一覧表示や画面遷移の詳細は、この Activity ではなく
 * AppNavHost や各 Screen に任せています。
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            // @AndroidEntryPoint が付いた Activity 上では、
            // Hilt が ViewModel に必要な依存関係を自動で解決してくれる。
            val todoListViewModel: TodoListViewModel = viewModel()

            SimpleTodoAppTheme {
                // 画面遷移の起点は AppNavHost にまとめている。
                AppNavHost(todoListViewModel = todoListViewModel)
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
