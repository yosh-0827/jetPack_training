package com.example.shinmasclerecord.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shinmasclerecord.data.DummyWorkoutRecords
import com.example.shinmasclerecord.ui.theme.page.HomeScreen
import com.example.shinmasclerecord.ui.theme.page.RecordEditScreen
import com.example.shinmasclerecord.viewModel.WorkoutRecordViewModel

private const val HOME_ROUTE = "home"
private const val RECORD_EDIT_ROUTE = "record_edit"

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val viewModel: WorkoutRecordViewModel = viewModel()
    val records by viewModel.records.collectAsState()

    NavHost(
        navController = navController,
        startDestination = HOME_ROUTE,  // 初めはホーム画面へ遷移
    ) {
        // Home画面の設定
        composable(HOME_ROUTE) {
            HomeScreen(
                records = records,
                onRecordClick = {},
                onAddClick = {
                    navController.navigate(RECORD_EDIT_ROUTE)
                },
            )
        }

        // 新規登録画面の設定
        composable(RECORD_EDIT_ROUTE) {
            RecordEditScreen(
                // 新規登録画面でonSaveClickが実行されたらカッコの中を実行する
                onSaveClick = {
                    record -> viewModel.insert(record)
                    navController.popBackStack()
                }
            )
        }
    }
}
