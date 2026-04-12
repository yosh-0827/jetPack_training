package com.example.masclerecord.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_records")
data class WorkoutRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String,
    val exerciseName: String,
    val weight: Float,
    val reps: Int,
    val sets: Int,
    val memo: String = "",
)
