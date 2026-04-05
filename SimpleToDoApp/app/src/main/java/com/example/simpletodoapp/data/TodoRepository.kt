package com.example.simpletodoapp.data

/**
 * DB操作の窓口
 * viewModelからDaoを操作しても良いが、責務が混ざるので
 * repositorを用意することで、そこから先のDB操作の責務をこいつに窓口してもらう。
 * */
class TodoRepository(
    private val todoDao: TodoDao
) {
    fun getAllTodos() = todoDao.getAll()

    suspend fun insert(todo: TodoEntity) = todoDao.insert(todo)

    suspend fun update(todo: TodoEntity) = todoDao.update(todo)

    suspend fun delete(todo: TodoEntity) = todoDao.delete(todo)
}