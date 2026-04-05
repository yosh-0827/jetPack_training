package com.example.simpletodoapp.model

/**
 * サンプルデータ
 **/
val sampleTodoItems = listOf(
    TodoUiModel(
        id = 1L,
        title = "牛乳を買う",
        description = "帰りにスーパーで1本買う",
        isDone = false
    ),
    TodoUiModel(
        id = 2L,
        title = "Room の記事を読む",
        description = "Entity と Dao の役割をざっくり理解する",
        isDone = true
    ),
    TodoUiModel(
        id = 3L,
        title = "Hilt のメモを整理する",
        description = "Module と Inject の関係をまとめる",
        isDone = false
    )
)