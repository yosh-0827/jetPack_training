package com.example.masclerecord.data.repository

import com.example.masclerecord.data.local.WorkoutRecordDao
import com.example.masclerecord.data.local.WorkoutRecordEntity
import kotlinx.coroutines.flow.Flow

class WorkoutRepository(
    private val workoutRecordDao: WorkoutRecordDao,
) {

    fun getAllRecords(): Flow<List<WorkoutRecordEntity>> {
        return workoutRecordDao.getAllRecords()
    }

    suspend fun getRecordById(id: Int): WorkoutRecordEntity? {
        return workoutRecordDao.getRecordById(id)
    }

    suspend fun insert(record: WorkoutRecordEntity) {
        workoutRecordDao.insert(record)
    }

    suspend fun update(record: WorkoutRecordEntity) {
        workoutRecordDao.update(record)
    }

    suspend fun delete(record: WorkoutRecordEntity) {
        workoutRecordDao.delete(record)
    }
}
