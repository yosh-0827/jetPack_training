package com.example.masclerecord.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.masclerecord.data.local.WorkoutRecordEntity
import com.example.masclerecord.ui.theme.MascleRecordTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    records: List<WorkoutRecordEntity>,
    onRecordClick: (Int) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
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

@Composable
private fun RecordList(
    records: List<WorkoutRecordEntity>,
    onRecordClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(records, key = { it.id }) { record ->
            WorkoutRecordCard(
                record = record,
                onClick = {
                    onRecordClick(record.id)
                },
            )
        }
    }
}

@Composable
private fun WorkoutRecordCard(
    record: WorkoutRecordEntity,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = record.date,
                style = MaterialTheme.typography.labelLarge,
            )
            Text(
                text = record.exerciseName,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = "${record.weight}kg / ${record.reps}回 / ${record.sets}セット",
                style = MaterialTheme.typography.bodyMedium,
            )
            if (record.memo.isNotBlank()) {
                Text(
                    text = record.memo,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}

@Composable
private fun EmptyHomeContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "まだ記録がありません",
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = "右下の追加ボタンから最初の記録を作れます。",
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    MascleRecordTheme {
        HomeScreen(
            records = listOf(
                WorkoutRecordEntity(
                    id = 1,
                    date = "2026-04-12",
                    exerciseName = "ベンチプレス",
                    weight = 60f,
                    reps = 10,
                    sets = 3,
                    memo = "最後のセットが少しきつかった",
                ),
            ),
            onRecordClick = {},
            onAddClick = {},
        )
    }
}
