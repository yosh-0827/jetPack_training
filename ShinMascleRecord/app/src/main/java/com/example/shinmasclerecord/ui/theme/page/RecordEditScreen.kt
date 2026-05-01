package com.example.shinmasclerecord.ui.theme.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)  // TopAppBarを使うために必要
@Composable
fun RecordEditScreen(
    modifier: Modifier = Modifier,
) {
    // 入力フォールドのstate
    // it: 入力が変わったら、その新しい文字を exerciseName等の変数 に入れる
    var date by remember { mutableStateOf("") }
    var exerciseName by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var reps by remember { mutableStateOf("") }
    var sets by remember { mutableStateOf("") }
    var memo by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        // TopAppBar: 画面の一番上に置くアプリバー
        topBar = {
            TopAppBar(
                title = {Text(text = "記録を追加")},
            )
        },
    ) { innerPadding ->
        // 入力画面のスタイル設定
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // 日付入力
            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                label = {Text(text = "日付")},
                modifier = Modifier.fillMaxWidth(),
            )
            // 種目
            OutlinedTextField(
                value = exerciseName,
                onValueChange = { exerciseName = it },
                label = {Text(text = "種目名")},
                modifier = Modifier.fillMaxWidth(),
            )
            // 重さ
            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = {Text(text = "重量")},
                modifier = Modifier.fillMaxWidth(),
            )
            // 回数
            OutlinedTextField(
                value = reps,
                onValueChange = { reps = it },
                label = {Text(text = "回数")},
                modifier = Modifier.fillMaxWidth(),
            )
            // セット数
            OutlinedTextField(
                value = sets,
                onValueChange = { sets = it },
                label = {Text(text = "セット数")},
                modifier = Modifier.fillMaxWidth(),
            )
            // めも
            OutlinedTextField(
                value = memo,
                onValueChange = { memo = it },
                label = {Text(text = "メモ")},
                modifier = Modifier.fillMaxWidth(),
            )

            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "保存")
            }
        }
    }
}