package com.example.shinmasclerecord.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shinmasclerecord.data.DummyWorkoutRecords
import com.example.shinmasclerecord.ui.theme.page.HomeScreen
import com.example.shinmasclerecord.ui.theme.page.RecordEditScreen

private const val HOME_ROUTE = "home"
private const val RECORD_EDIT_ROUTE = "record_edit"

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HOME_ROUTE,  // 初めはホーム画面へ遷移
    ) {
        // Home画面の設定
        composable(HOME_ROUTE) {
            HomeScreen(
                records = DummyWorkoutRecords.homeRecords,
                onRecordClick = {},
                onAddClick = {
                    navController.navigate(RECORD_EDIT_ROUTE)
                },
            )
        }

        // 新規登録画面の設定
        composable(RECORD_EDIT_ROUTE) {
            RecordEditScreen()
        }
    }
}
