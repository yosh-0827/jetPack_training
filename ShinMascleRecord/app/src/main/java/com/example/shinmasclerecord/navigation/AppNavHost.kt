package com.example.shinmasclerecord.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController

// 画面名＋パス定義
private const val HOME_ROUTE = "home"
private const val RECORD_EDIT_ROUTE = "record_edit"
private const val RECORD_ID_ARGUMENT = "recordId"
private const val RECORD_EDIT_WITH_ID_ROUTE = "record_edit/{$RECORD_ID_ARGUMENT}"
private const val RECORD_DETAIL_ROUTE = "record_detail"

@Composable
fun AppNavHost() {
//    val navController = rememberNavController()
//
//    NavHost(
//        navController = navController,
//        startDestination = HOME_ROUTE,
//    ){
//        composable(HOME_ROUTE) {
//            val veiwModel: Home
//        }
//    }
}