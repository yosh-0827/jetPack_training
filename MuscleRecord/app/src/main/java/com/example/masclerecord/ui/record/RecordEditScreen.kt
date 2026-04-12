package com.example.masclerecord.ui.record

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.masclerecord.ui.theme.MascleRecordTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordEditScreen(
    isEditMode: Boolean,
    date: TextFieldValue,
    exerciseName: TextFieldValue,
    weight: TextFieldValue,
    reps: TextFieldValue,
    sets: TextFieldValue,
    memo: TextFieldValue,
    errorMessage: String?,
    onDateChange: (TextFieldValue) -> Unit,
    onExerciseNameChange: (TextFieldValue) -> Unit,
    onWeightChange: (TextFieldValue) -> Unit,
    onRepsChange: (TextFieldValue) -> Unit,
    onSetsChange: (TextFieldValue) -> Unit,
    onMemoChange: (TextFieldValue) -> Unit,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
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
                    Text(text = if (isEditMode) "記録を編集" else "記録を追加")
                },
            )
        },
    ) { innerPadding ->
        RecordEditContent(
            isEditMode = isEditMode,
            date = date,
            exerciseName = exerciseName,
            weight = weight,
            reps = reps,
            sets = sets,
            memo = memo,
            errorMessage = errorMessage,
            onDateChange = onDateChange,
            onExerciseNameChange = onExerciseNameChange,
            onWeightChange = onWeightChange,
            onRepsChange = onRepsChange,
            onSetsChange = onSetsChange,
            onMemoChange = onMemoChange,
            onBackClick = onBackClick,
            onSaveClick = onSaveClick,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Composable
private fun RecordEditContent(
    isEditMode: Boolean,
    date: TextFieldValue,
    exerciseName: TextFieldValue,
    weight: TextFieldValue,
    reps: TextFieldValue,
    sets: TextFieldValue,
    memo: TextFieldValue,
    errorMessage: String?,
    onDateChange: (TextFieldValue) -> Unit,
    onExerciseNameChange: (TextFieldValue) -> Unit,
    onWeightChange: (TextFieldValue) -> Unit,
    onRepsChange: (TextFieldValue) -> Unit,
    onSetsChange: (TextFieldValue) -> Unit,
    onMemoChange: (TextFieldValue) -> Unit,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val localContext = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = if (isEditMode) {
                "既存の記録を修正するための入力画面です。"
            } else {
                "まずは1件保存できることを目標にした、シンプルな入力画面です。"
            },
            style = MaterialTheme.typography.bodyMedium,
        )

        RecordTextField(
            value = date,
            onValueChange = onDateChange,
            label = "日付",
            placeholder = "2026-04-12",
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            // 日付入力は手入力も残しつつ、カレンダーから選べるようにしています。
            Button(
                onClick = {
                    showDatePickerDialog(
                        currentDateText = date.text,
                        onDateSelected = { selectedDate ->
                            onDateChange(TextFieldValue(selectedDate))
                        },
                        dialogContext = localContext,
                    )
                },
            ) {
                Text(text = "日付を選ぶ")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "手入力が面倒なときに使えます",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 12.dp),
            )
        }
        RecordTextField(
            value = exerciseName,
            onValueChange = onExerciseNameChange,
            label = "種目名",
            placeholder = "ベンチプレス",
        )
        RecordTextField(
            value = weight,
            onValueChange = onWeightChange,
            label = "重量(kg)",
            placeholder = "60",
            keyboardType = KeyboardType.Decimal,
        )
        RecordTextField(
            value = reps,
            onValueChange = onRepsChange,
            label = "回数",
            placeholder = "10",
            keyboardType = KeyboardType.Number,
        )
        RecordTextField(
            value = sets,
            onValueChange = onSetsChange,
            label = "セット数",
            placeholder = "3",
            keyboardType = KeyboardType.Number,
        )
        RecordTextField(
            value = memo,
            onValueChange = onMemoChange,
            label = "メモ",
            placeholder = "最後のセットが重かった",
        )

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        // 保存ボタンは最後にまとめると、入力と保存の流れを追いやすいです。
        Button(
            onClick = onSaveClick,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 14.dp),
        ) {
            Text(text = if (isEditMode) "更新する" else "保存する")
        }

        TextButton(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "保存せず戻る")
        }
    }
}

private fun showDatePickerDialog(
    currentDateText: String,
    onDateSelected: (String) -> Unit,
    dialogContext: android.content.Context,
) {
    val calendar = parseDateToCalendar(currentDateText)

    DatePickerDialog(
        dialogContext,
        { _, year, month, dayOfMonth ->
            val selectedCalendar = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }
            onDateSelected(formatCalendar(selectedCalendar))
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH),
    ).show()
}

private fun parseDateToCalendar(dateText: String): Calendar {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.JAPAN)
    val parsedDate = formatter.parse(dateText)

    return Calendar.getInstance().apply {
        time = parsedDate ?: time
    }
}

private fun formatCalendar(calendar: Calendar): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.JAPAN).format(calendar.time)
}

@Composable
private fun RecordTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        label = {
            Text(text = label)
        },
        placeholder = {
            Text(text = placeholder)
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true,
    )
}

@Preview(showBackground = true)
@Composable
private fun RecordEditScreenPreview() {
    MascleRecordTheme {
        RecordEditScreen(
            isEditMode = false,
            date = TextFieldValue("2026-04-12"),
            exerciseName = TextFieldValue("ベンチプレス"),
            weight = TextFieldValue("60"),
            reps = TextFieldValue("10"),
            sets = TextFieldValue("3"),
            memo = TextFieldValue(""),
            errorMessage = null,
            onDateChange = {},
            onExerciseNameChange = {},
            onWeightChange = {},
            onRepsChange = {},
            onSetsChange = {},
            onMemoChange = {},
            onBackClick = {},
            onSaveClick = {},
        )
    }
}
