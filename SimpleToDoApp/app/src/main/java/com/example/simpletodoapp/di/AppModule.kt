package com.example.simpletodoapp.di

import android.content.Context
import androidx.room.Room
import com.example.simpletodoapp.data.AppDatabase
import com.example.simpletodoapp.data.TodoDao
import com.example.simpletodoapp.data.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt に「どうやってインスタンスを作るか」を教える Module。
 *
 * 今回は Room まわりの依存関係をここにまとめる。
 * これにより MainActivity で手動生成していた
 * - AppDatabase
 * - TodoDao
 * - TodoRepository
 * を Hilt が代わりに用意してくれるようになる。
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Room の Database 本体を 1 つだけ生成する。
     *
     * @Singleton を付けることで、アプリ全体で同じインスタンスを使い回す。
     */
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "todo_database"
        ).build()
    }

    /**
     * Database から TodoDao を取り出して提供する。
     */
    @Provides
    @Singleton
    fun provideTodoDao(
        appDatabase: AppDatabase,
    ): TodoDao {
        return appDatabase.todoDao()
    }

    /**
     * Dao を使って Repository を生成する。
     *
     * ViewModel はこの Repository だけを知っていればよくなり、
     * Room の詳細から少し距離を置ける。
     */
    @Provides
    @Singleton
    fun provideTodoRepository(
        todoDao: TodoDao,
    ): TodoRepository {
        return TodoRepository(todoDao)
    }
}
