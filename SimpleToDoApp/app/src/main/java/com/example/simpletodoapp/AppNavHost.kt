package com.example.simpletodoapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simpletodoapp.components.TodoCreateScreen
import com.example.simpletodoapp.components.TodoDetailScreen
import com.example.simpletodoapp.components.TodoListScreen

// 画面遷移で使うルート名。
// 詳細画面は、どの Todo を開いたか分かるように id を route に含める。
private const val TODO_LIST_ROUTE = "todo_list"
private const val TODO_CREATE_ROUTE = "todo_create"
private const val TODO_DETAIL_ROUTE = "todo_detail"

/**
 * Compose Navigation の入口。
 *
 * NavHost は「どの route でどの画面を表示するか」をまとめる場所。
 * いわば Compose 側の画面遷移表です。
 *
 * 今回は NavController を各画面に直接渡さず、
 * 「ボタンが押されたときに何をするか」をラムダで渡しています。
 * そのほうが各画面の責務が小さくなり、後で読みやすくなります。
 */
@Composable
fun AppNavHost(
    todoListViewModel: TodoListViewModel,
) {
    // NavController は「今どの画面にいるか」と「どこへ移動するか」を管理する。
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = TODO_LIST_ROUTE
    ) {
        // 一覧画面。
        // + ボタンが押されたら新規作成画面へ遷移する。
        composable(TODO_LIST_ROUTE) {
            TodoListScreen(
                todoItems = todoListViewModel.todoItems,
                onAddClick = {
                    navController.navigate(TODO_CREATE_ROUTE)  //画面へ移動
                },
                onTodoClick = { todo ->
                    navController.navigate("$TODO_DETAIL_ROUTE/${todo.id}")
                },
                onCheckedChange = { targetTodo, isChecked ->
                    todoListViewModel.onCheckedChange(targetTodo, isChecked)
                }
            )
        }

        // 新規作成画面。
        // 保存したら ViewModel 経由で DB に保存し、その後一覧画面へ戻る。
        composable(TODO_CREATE_ROUTE) {
            TodoCreateScreen(
                onSaveClick = { title, description ->
                    todoListViewModel.addTodo(title, description)
                    navController.popBackStack()
                },
                onBackClick = {
                    navController.popBackStack()  // 戻る操作
                }
            )
        }

        // 詳細画面。
        // route の id から Todo を特定し、その Todo の更新・削除を行う。
        composable(
            route = "$TODO_DETAIL_ROUTE/{todoId}",
            arguments = listOf(
                navArgument("todoId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val todoId = backStackEntry.arguments?.getLong("todoId") ?: return@composable
            val todo = todoListViewModel.findTodoById(todoId)

            TodoDetailScreen(
                todoItem = todo,
                onBackClick = {
                    navController.popBackStack()
                },
                onSaveClick = { title, description, isDone ->
                    todoListViewModel.updateTodo(
                        todoId = todoId,
                        title = title,
                        description = description,
                        isDone = isDone
                    )
                    navController.popBackStack()
                },
                onDeleteClick = {
                    todoListViewModel.deleteTodo(todoId)
                    navController.popBackStack()
                }
            )
        }
    }
}
