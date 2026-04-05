package com.example.simpletodoapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simpletodoapp.ui.theme.SimpleTodoAppTheme

/**
 * Todo の新規作成画面。
 *
 * ここでは title / description の入力状態だけを持つ。
 * 実際に DB に保存する処理はこの画面では行わず、
 * onSaveClick を通して外側(AppNavHost -> ViewModel)へ渡す。
 *
 * つまりこの Screen は、
 * - 入力欄を表示する
 * - 入力値を親へ返す
 * ところまでを担当する。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoCreateScreen(
    onSaveClick: (String, String) -> Unit,
    onBackClick: () -> Unit,
) {
    // rememberSaveable を使うことで、簡単な入力状態を Compose 側で保持する。
    // 画面回転などでも復元されやすい。
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "新規作成")
                },
                navigationIcon = {
                    TextButton(onClick = onBackClick) {
                        Text(text = "戻る")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Todoを追加",
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

            Button(
                onClick = {
                    // この画面では保存処理そのものは知らず、
                    // 入力された文字列を親へ返すだけにしている。
                    onSaveClick(title, description)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = title.isNotBlank()
            ) {
                Text(text = "保存")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TodoCreateScreenPreview() {
    SimpleTodoAppTheme {
        TodoCreateScreen(
            onSaveClick = { _, _ -> },
            onBackClick = {}
        )
    }
}
