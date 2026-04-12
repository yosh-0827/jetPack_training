package com.example.masclerecord.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.masclerecord.data.local.AppDatabase
import com.example.masclerecord.data.local.WorkoutRecordEntity
import com.example.masclerecord.data.repository.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecordDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WorkoutRepository(
        workoutRecordDao = AppDatabase.getDatabase(application).workoutRecordDao(),
    )

    private val _record = MutableStateFlow<WorkoutRecordEntity?>(null)
    val record: StateFlow<WorkoutRecordEntity?> = _record

    fun loadRecord(recordId: Int) {
        viewModelScope.launch {
            _record.value = repository.getRecordById(recordId)
        }
    }

    fun deleteRecord(onDeleted: () -> Unit) {
        val currentRecord = _record.value ?: return

        viewModelScope.launch {
            repository.delete(currentRecord)
            onDeleted()
        }
    }
}
