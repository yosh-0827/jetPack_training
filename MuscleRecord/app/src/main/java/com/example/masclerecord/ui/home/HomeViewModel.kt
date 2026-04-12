package com.example.masclerecord.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.masclerecord.data.local.AppDatabase
import com.example.masclerecord.data.local.WorkoutRecordEntity
import com.example.masclerecord.data.repository.WorkoutRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WorkoutRepository(
        workoutRecordDao = AppDatabase.getDatabase(application).workoutRecordDao(),
    )

    // Room の Flow を画面で扱いやすい StateFlow に変換しています。
    val records: StateFlow<List<WorkoutRecordEntity>> = repository.getAllRecords().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList(),
    )
}
