package com.example.simpletodoapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simpletodoapp.model.TodoUiModel
import com.example.simpletodoapp.model.sampleTodoItems

@OptIn(ExperimentalMaterial3Api::class)  // TopAppBar が experimental 扱いなので、「今回は使います」と明示しています
@Composable
fun TodoListScreen(
    // 表示するtodoリストを受け取ります
    todoItems: List<TodoUiModel>,
    onAddClick: () -> Unit,
    onTodoClick: (TodoUiModel) -> Unit,
    onCheckedChange: (TodoUiModel, Boolean) -> Unit,
) {
    Scaffold(
        // 画面全体のレイアウトの土台
        modifier = Modifier.fillMaxSize(),
        // 画面上のバーを定義
        topBar = {
            // Material Design の上部バー
            TopAppBar(
                title = {
                    Text(text = "Todo一覧")
                }
            )
        },
        // 右下に浮く丸いボタン
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick
            ) {
                Text(text = "+")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),  // topBar などと重ならないように余白情報を受け取っています
            // 一覧全体の外側に16dpの余白を付けています
            contentPadding = PaddingValues(16.dp),
            // 各Todoカードの間を12dp空けています
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // todoItems の数だけ一覧を作ります。1件ずつ todoItem として取り出しています
            items(todoItems) { todoItem ->
                TodoItemCard(
                    todoItem = todoItem,
                    onTodoClick = onTodoClick,
                    onCheckedChange = { isChecked ->
                        onCheckedChange(todoItem, isChecked)
                    }
                )
            }
        }
    }
}