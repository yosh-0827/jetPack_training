package com.example.simpletodoapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simpletodoapp.model.TodoUiModel
import com.example.simpletodoapp.ui.theme.SimpleTodoAppTheme

/**
 * Todo の詳細・更新・削除をまとめた画面。
 *
 * Todo 自体は外から受け取り、この画面では入力欄用のローカル状態にコピーして編集する。
 * 保存や削除の実処理は持たず、ボタン押下時に親(AppNavHost -> ViewModel)へ返す。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailScreen(
    todoItem: TodoUiModel?,
    onBackClick: () -> Unit,
    onSaveClick: (String, String, Boolean) -> Unit,
    onDeleteClick: () -> Unit,
) {
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var isDone by rememberSaveable { mutableStateOf(false) }

    // Todo が取得できたら、その内容を編集用の入力欄へ反映する。
    LaunchedEffect(todoItem) {
        if (todoItem != null) {
            title = todoItem.title
            description = todoItem.description
            isDone = todoItem.isDone
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "詳細")
                },
                navigationIcon = {
                    TextButton(onClick = onBackClick) {
                        Text(text = "戻る")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (todoItem == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "Todoが見つかりませんでした。")
                Button(onClick = onBackClick) {
                    Text(text = "一覧へ戻る")
                }
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Todoを編集",
                style = MaterialTheme.typography.headlineSmall
            )

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "タイトル")
                },
                singleLine = true
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "説明")
                },
                minLines = 4
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Checkbox(
                    checked = isDone,
                    onCheckedChange = { checked ->
                        isDone = checked
                    }
                )
                Text(text = "完了")
            }

            Button(
                onClick = {
                    onSaveClick(title, description, isDone)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = title.isNotBlank()
            ) {
                Text(text = "更新")
            }

            TextButton(
                onClick = onDeleteClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "削除")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TodoDetailScreenPreview() {
    SimpleTodoAppTheme {
        TodoDetailScreen(
            todoItem = TodoUiModel(
                id = 1L,
                title = "買い物に行く",
                description = "牛乳とパンを買う",
                isDone = false
            ),
            onBackClick = {},
            onSaveClick = { _, _, _ -> },
            onDeleteClick = {}
        )
    }
}
