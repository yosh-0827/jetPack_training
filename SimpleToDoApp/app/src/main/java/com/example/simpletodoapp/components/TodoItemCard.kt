package com.example.simpletodoapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.simpletodoapp.model.TodoUiModel

/*
 * Todo1件を表示するためのUI関数
 */
@Composable
fun TodoItemCard(todoItem: TodoUiModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {}
    ) {
        // カードの中身
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            // 縦方向の中央に
            verticalAlignment = Alignment.CenterVertically,
            // 横の部品の間を12dp空けています
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 完了状態をチェックボックスで表示しています
            Checkbox(
                checked = todoItem.isDone,
                onCheckedChange = {}
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = todoItem.title,
                    // 少し大きめの文字スタイル
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = todoItem.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}