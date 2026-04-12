package com.example.masclerecord.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.masclerecord.data.local.WorkoutRecordEntity
import com.example.masclerecord.ui.theme.MascleRecordTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordDetailScreen(
    record: WorkoutRecordEntity?,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    TextButton(onClick = onBackClick) {
                        Text(text = "戻る")
                    }
                },
                title = {
                    Text(text = "記録詳細")
                },
            )
        },
    ) { innerPadding ->
        if (record == null) {
            EmptyDetailContent(
                modifier = Modifier.padding(innerPadding),
            )
        } else {
            RecordDetailContent(
                record = record,
                onEditClick = onEditClick,
                onDeleteClick = onDeleteClick,
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}

@Composable
private fun RecordDetailContent(
    record: WorkoutRecordEntity,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = "保存した1件を確認するための画面です。",
            style = MaterialTheme.typography.bodyMedium,
        )

        DetailCard(
            label = "日付",
            value = record.date,
        )
        DetailCard(
            label = "種目名",
            value = record.exerciseName,
        )
        DetailCard(
            label = "重量",
            value = "${record.weight} kg",
        )
        DetailCard(
            label = "回数",
            value = "${record.reps} 回",
        )
        DetailCard(
            label = "セット数",
            value = "${record.sets} セット",
        )
        DetailCard(
            label = "メモ",
            value = if (record.memo.isBlank()) "未入力" else record.memo,
        )

        Button(
            onClick = onEditClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "この記録を編集する")
        }

        // まずは削除の流れを追いやすくするため、詳細画面から直接削除できるようにしています。
        Button(
            onClick = onDeleteClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError,
            ),
        ) {
            Text(text = "この記録を削除する")
        }
    }
}

@Composable
private fun DetailCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
private fun EmptyDetailContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "記録が見つかりませんでした。",
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = "一覧からもう一度選び直してください。",
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecordDetailScreenPreview() {
    MascleRecordTheme {
        RecordDetailScreen(
            record = WorkoutRecordEntity(
                id = 1,
                date = "2026-04-12",
                exerciseName = "ベンチプレス",
                weight = 60f,
                reps = 10,
                sets = 3,
                memo = "最後のセットが少し重かった",
            ),
            onBackClick = {},
            onEditClick = {},
            onDeleteClick = {},
        )
    }
}
