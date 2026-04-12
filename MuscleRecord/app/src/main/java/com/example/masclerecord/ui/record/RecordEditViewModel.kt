package com.example.masclerecord.ui.record

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.masclerecord.data.local.AppDatabase
import com.example.masclerecord.data.local.WorkoutRecordEntity
import com.example.masclerecord.data.repository.WorkoutRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.launch

class RecordEditViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WorkoutRepository(
        workoutRecordDao = AppDatabase.getDatabase(application).workoutRecordDao(),
    )

    // TextFieldValue を使うと、日本語IMEの変換中状態も保持しやすくなります。
    var date by mutableStateOf(TextFieldValue(todayText()))
        private set
    var exerciseName by mutableStateOf(TextFieldValue(""))
        private set
    var weight by mutableStateOf(TextFieldValue(""))
        private set
    var reps by mutableStateOf(TextFieldValue(""))
        private set
    var sets by mutableStateOf(TextFieldValue(""))
        private set
    var memo by mutableStateOf(TextFieldValue(""))
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var editingRecordId by mutableStateOf<Int?>(null)
        private set

    val isEditMode: Boolean
        get() = editingRecordId != null

    fun updateDate(value: TextFieldValue) {
        date = value
    }

    fun updateExerciseName(value: TextFieldValue) {
        exerciseName = value
    }

    fun updateWeight(value: TextFieldValue) {
        weight = value
    }

    fun updateReps(value: TextFieldValue) {
        reps = value
    }

    fun updateSets(value: TextFieldValue) {
        sets = value
    }

    fun updateMemo(value: TextFieldValue) {
        memo = value
    }

    fun loadRecord(recordId: Int) {
        if (editingRecordId == recordId) {
            return
        }

        viewModelScope.launch {
            val record = repository.getRecordById(recordId) ?: return@launch

            // 編集時は DB の値をそのまま入力欄へ入れて、画面を共用しています。
            editingRecordId = record.id
            date = TextFieldValue(record.date)
            exerciseName = TextFieldValue(record.exerciseName)
            weight = TextFieldValue(record.weight.toString())
            reps = TextFieldValue(record.reps.toString())
            sets = TextFieldValue(record.sets.toString())
            memo = TextFieldValue(record.memo)
        }
    }

    fun saveRecord(onSaved: () -> Unit) {
        val dateText = date.text.trim()
        val exerciseNameText = exerciseName.text.trim()
        val weightText = weight.text.trim()
        val repsText = reps.text.trim()
        val setsText = sets.text.trim()
        val memoText = memo.text.trim()

        val weightValue = weightText.toFloatOrNull()
        val repsValue = repsText.toIntOrNull()
        val setsValue = setsText.toIntOrNull()

        // 先に入力値を確認しておくと、保存処理の流れが追いやすくなります。
        if (
            dateText.isBlank() ||
            exerciseNameText.isBlank() ||
            weightValue == null ||
            repsValue == null ||
            setsValue == null
        ) {
            errorMessage = "必須項目を正しく入力してください。"
            return
        }

        errorMessage = null

        viewModelScope.launch {
            val record = WorkoutRecordEntity(
                id = editingRecordId ?: 0,
                date = dateText,
                exerciseName = exerciseNameText,
                weight = weightValue,
                reps = repsValue,
                sets = setsValue,
                memo = memoText,
            )

            if (isEditMode) {
                repository.update(record)
            } else {
                repository.insert(record)
            }
            onSaved()
        }
    }

    private fun todayText(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.JAPAN).format(Date())
    }
}
