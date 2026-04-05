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

/**
 * Todo 一覧画面。
 *
 * この画面自身は状態を持たず、表示に必要な Todo 一覧と
 * ボタン押下時のイベント関数を外から受け取る。
 *
 * そのため、
 * - 何を表示するか: todoItems
 * - + を押したら何をするか: onAddClick
 * - カードを押したら何をするか: onTodoClick
 * - チェック変更時に何をするか: onCheckedChange
 * を親側(MainActivity / AppNavHost / ViewModel)が決められる。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    todoItems: List<TodoUiModel>,
    onAddClick: () -> Unit,
    onTodoClick: (TodoUiModel) -> Unit,
    onCheckedChange: (TodoUiModel, Boolean) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Todo一覧")
                }
            )
        },
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
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(todoItems) { todoItem ->
                TodoItemCard(
                    todoItem = todoItem,
                    onTodoClick = onTodoClick,
                    onCheckedChange = { isChecked ->
                        // Card 側からは Boolean だけ返ってくるので、
                        // ここで「どの Todo が変わったか」を付け足して親へ返す。
                        onCheckedChange(todoItem, isChecked)
                    }
                )
            }
        }
    }
}
