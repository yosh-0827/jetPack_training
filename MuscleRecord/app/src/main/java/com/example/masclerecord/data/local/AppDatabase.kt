package com.example.masclerecord.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [WorkoutRecordEntity::class],
    version = 1,
    exportSchema = false,
)
/**
 * RoomDatabase を継承して、「Room用のDBクラス」を作っています
 * abstract なのは、実際の中身を Room が自動生成するからです。自分で全部実装するのではなく、Room に「こういうDBが欲しい」と宣言しています
 *
 * */
abstract class AppDatabase : RoomDatabase() {

    // このDBから WorkoutRecordDao を取り出す関数です
    abstract fun workoutRecordDao(): WorkoutRecordDao

    // companion object は、Javaでいう static に近いものです
    companion object {
        // DBのインスタンスを1回だけ作って保持するため.つまり 毎回DBを作り直さない ようにしています
        @Volatile // 「DBを安全に1つだけ使うためのおまじない」
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // INSTANCE があればそれを返し、なければ右側の処理に進みます
            // synchronizedは同時に2か所から getDatabase() が呼ばれても、
            // DBが2個作られないようにする ための制御です
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "muscle_record_database",
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
