package com.example.shinmasclerecord.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shinmasclerecord.data.AppDatabase
import com.example.shinmasclerecord.data.WorkoutRecordEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WorkoutRecordViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase
        .getDatabase(application)
        .workoutRecordDao()

    val records: StateFlow<List<WorkoutRecordEntity>> = dao.getAllRecords()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList(),
        )

    /*
    * 保存ボタンを押すと、入力内容から WorkoutRecordEntity を作ります。
    * それを ViewModel に渡すと、ViewModel が dao.insert(record) を呼んで Room に保存します
    * */
    // Room の insert は suspend 関数なので、viewModelScope.launch の中で呼びます。
    fun insert(record: WorkoutRecordEntity) {
        viewModelScope.launch {
            dao.insert(record)
        }
    }
}