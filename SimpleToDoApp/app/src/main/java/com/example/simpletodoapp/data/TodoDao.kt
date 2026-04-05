package com.example.simpletodoapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * DB(Room)への操作を書く場所
 * Data Access Object の略
 * すごく簡単に言うとSQL分を書くところ
 *
 * 「Roomの書き込み系は suspend にしておくことが多い」
 *  DB操作は少し重い処理なので、メインスレッドでそのままやらないため。
 * */
@Dao // Daoであることを示す
interface TodoDao {

    // Flow: 値が変わるたびに流れてくるストリーム
    @Query("SELECT * FROM todos")
    fun getAll(): Flow<List<TodoEntity>>

    @Insert
    suspend fun insert(todo: TodoEntity)

    @Update
    suspend fun update(todo: TodoEntity)

    @Delete
    suspend fun delete(todo: TodoEntity)
}