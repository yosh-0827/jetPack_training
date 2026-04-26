package com.example.shinmasclerecord.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/*
* DaoがあることでDBとやりとりできる
* それぐらいの認識で良さげ。
*/
@Database(
    entities =[WorkoutRecordEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    // Daoを取り出す
    abstract fun workoutRecordDao(): WorkoutRecordDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "shin_mascle_record_database",
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}