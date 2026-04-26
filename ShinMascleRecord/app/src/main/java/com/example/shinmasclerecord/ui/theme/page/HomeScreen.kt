package com.example.shinmasclerecord.ui.theme.page

import android.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shinmasclerecord.data.WorkoutRecordEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    records: List<WorkoutRecordEntity>,  // 表示する記録一覧そのもの
    onRecordClick: () -> Unit,  // 1件タップした時に何をするか
    onAddClick: () -> Unit,  // 追加ボタンを押した時に何をするか
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "筋トレ記録一覧")
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Text(text = "追加")
            }
        },
    ) { innerPadding ->
        if (records.isEmpty()) {
            EmptyHomeContent(
                modifier = Modifier.padding(innerPadding),
            )
        } else {
            RecordList(
                records = records,
                onRecordClick = onRecordClick,
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}

/*
* 記録が何もない時の画面
* */
@Composable
fun EmptyHomeContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "記録がありません",
            style = MaterialTheme.typography.titleMedium,
            )
        Text(
            text = "右下の追加ボタンから最初の記録を作れます",
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

/**
 * データがある時の一覧画面
 * @param records: 表示したい全件
 * @param onRecordClick: レコード選択時の関数
 * @param modifier: 見栄えの設定
 * */
@Composable
fun RecordList(
    records: List<WorkoutRecordEntity>,
    onRecordClick: () -> Unit,
    modifier: Modifier = Modifier,
){
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),  // 一覧の外側の余白
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // 「records を1件ずつ取り出す」部分
        items(
            items = records,
            key = { record -> record.id },  // Compose に「この行はこの id のデータだよ」と教えるため
            // 1件ずつ record という名前で使う
            ) { record ->
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "日付: ${record.date}",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "種目: ${record.exerciseName}",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "重量: ${record.weight}kg / 回数: ${record.reps} / セット: ${record.sets}",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}