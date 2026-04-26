package com.example.shinmasclerecord.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutRecordDao {

    @Query("SELECT * FROM workout_records ORDER BY date DESC, id DESC")
    fun getAllRecords(): Flow<List<WorkoutRecordEntity>>

    @Query("SELECT * FROM workout_records WHERE id = :id")
    suspend fun getRecordById(id: Int): WorkoutRecordEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: WorkoutRecordEntity)

    @Update
    suspend fun update(record: WorkoutRecordEntity)

    @Delete
    suspend fun delete(record: WorkoutRecordEntity)
}