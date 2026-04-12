package com.example.masclerecord.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.masclerecord.ui.detail.RecordDetailScreen
import com.example.masclerecord.ui.detail.RecordDetailViewModel
import com.example.masclerecord.ui.home.HomeScreen
import com.example.masclerecord.ui.home.HomeViewModel
import com.example.masclerecord.ui.record.RecordEditScreen
import com.example.masclerecord.ui.record.RecordEditViewModel

private const val HOME_ROUTE = "home"
private const val RECORD_EDIT_ROUTE = "record_edit"
private const val RECORD_ID_ARGUMENT = "recordId"
private const val RECORD_EDIT_WITH_ID_ROUTE = "record_edit/{$RECORD_ID_ARGUMENT}"
private const val RECORD_DETAIL_ROUTE = "record_detail"

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HOME_ROUTE,
    ) {
        composable(HOME_ROUTE) {
            val viewModel: HomeViewModel = viewModel()
            val records by viewModel.records.collectAsState()
            HomeScreen(
                records = records,
                onRecordClick = { recordId ->
                    navigateToRecordDetail(navController, recordId)
                },
                onAddClick = {
                    navigateToRecordEdit(navController)
                },
            )
        }
        composable(RECORD_EDIT_ROUTE) {
            val viewModel: RecordEditViewModel = viewModel()
            RecordEditScreen(
                isEditMode = viewModel.isEditMode,
                date = viewModel.date,
                exerciseName = viewModel.exerciseName,
                weight = viewModel.weight,
                reps = viewModel.reps,
                sets = viewModel.sets,
                memo = viewModel.memo,
                errorMessage = viewModel.errorMessage,
                onDateChange = viewModel::updateDate,
                onExerciseNameChange = viewModel::updateExerciseName,
                onWeightChange = viewModel::updateWeight,
                onRepsChange = viewModel::updateReps,
                onSetsChange = viewModel::updateSets,
                onMemoChange = viewModel::updateMemo,
                onBackClick = {
                    navController.popBackStack()
                },
                onSaveClick = {
                    viewModel.saveRecord {
                        navController.popBackStack()
                    }
                },
            )
        }
        composable(
            route = RECORD_EDIT_WITH_ID_ROUTE,
            arguments = listOf(
                navArgument(RECORD_ID_ARGUMENT) {
                    type = NavType.IntType
                },
            ),
        ) { backStackEntry ->
            val recordId = backStackEntry.arguments?.getInt(RECORD_ID_ARGUMENT) ?: return@composable
            val viewModel: RecordEditViewModel = viewModel()

            // 編集画面では最初に対象レコードを読み込んで、既存値をフォームへ入れます。
            LaunchedEffect(recordId) {
                viewModel.loadRecord(recordId)
            }

            RecordEditScreen(
                isEditMode = viewModel.isEditMode,
                date = viewModel.date,
                exerciseName = viewModel.exerciseName,
                weight = viewModel.weight,
                reps = viewModel.reps,
                sets = viewModel.sets,
                memo = viewModel.memo,
                errorMessage = viewModel.errorMessage,
                onDateChange = viewModel::updateDate,
                onExerciseNameChange = viewModel::updateExerciseName,
                onWeightChange = viewModel::updateWeight,
                onRepsChange = viewModel::updateReps,
                onSetsChange = viewModel::updateSets,
                onMemoChange = viewModel::updateMemo,
                onBackClick = {
                    navController.popBackStack()
                },
                onSaveClick = {
                    viewModel.saveRecord {
                        navController.popBackStack()
                    }
                },
            )
        }
        composable(
            route = "$RECORD_DETAIL_ROUTE/{$RECORD_ID_ARGUMENT}",
            arguments = listOf(
                navArgument(RECORD_ID_ARGUMENT) {
                    type = NavType.IntType
                },
            ),
        ) { backStackEntry ->
            val recordId = backStackEntry.arguments?.getInt(RECORD_ID_ARGUMENT) ?: return@composable
            val viewModel: RecordDetailViewModel = viewModel()
            val record by viewModel.record.collectAsState()

            // recordId が変わったタイミングで詳細を読み直します。
            LaunchedEffect(recordId) {
                viewModel.loadRecord(recordId)
            }

            RecordDetailScreen(
                record = record,
                onBackClick = {
                    navController.popBackStack()
                },
                onEditClick = {
                    navigateToRecordEdit(navController, recordId)
                },
                onDeleteClick = {
                    viewModel.deleteRecord {
                        navController.popBackStack()
                    }
                },
            )
        }
    }
}

private fun navigateToRecordEdit(navController: NavHostController) {
    // 画面名を関数に閉じ込めておくと、遷移先が増えても追いやすいです。
    navController.navigate(RECORD_EDIT_ROUTE)
}

private fun navigateToRecordEdit(
    navController: NavHostController,
    recordId: Int,
) {
    navController.navigate("record_edit/$recordId")
}

private fun navigateToRecordDetail(
    navController: NavHostController,
    recordId: Int,
) {
    navController.navigate("$RECORD_DETAIL_ROUTE/$recordId")
}
