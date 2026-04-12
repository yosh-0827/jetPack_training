package com.example.learnnavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.learnnavigation.ui.theme.LearnNavigationTheme

// 画面の名前をまとめている場所
object SampleRoute {
    const val Home = "home"
    const val Detail = "detail/{itemId}"

    fun detail(itemId: Int): String = "detail/$itemId"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearnNavigationTheme {
                SampleApp()
            }
        }
    }
}

/**
 * navController
 * 画面移動を指示する人
 *
 * NavHost
 * どの画面を持っているかを登録する場所
 *
 * composable(...)
 * 1画面ずつの定義
 * */
@Composable
fun SampleApp() {
    // vavigationインスタンス
    val navController = rememberNavController()

    // このアプリの画面遷移はここで管理します。最初の画面は Home です
    NavHost(
        // コンストラクタにnavConを渡す。
        navController = navController,
        startDestination = SampleRoute.Home
    ) {
        // 各画面の登録
        /**
         * 1 SampleRoute.detail(itemId = 1) を呼ぶ
         * 2 "detail/1" という文字列ができる
         * 3 navigate(...) でその画面へ移動する
         * */
        composable(route = SampleRoute.Home) {
            HomeScreen(
                // Detail画面へ移動して、そのとき itemId = 1 を渡す
                onMoveDetail = {
                    navController.navigate(SampleRoute.detail(itemId = 1))
                }
            )
        }

        // 各画面の登録
        composable(
            route = SampleRoute.Detail,
            // 引数の詳細設定
            arguments = listOf(
                // itemId という引数を受け取る
                navArgument("itemId") {
                    type = NavType.IntType  // 引数は Int 型
                }
            )
            // backStackEntity: この画面に来たときの情報が入っているもの
        ) { backStackEntry ->
            // 渡された itemId を取り出して、もし無ければ 0 を使う
            val itemId = backStackEntry.arguments?.getInt("itemId") ?: 0

            DetailScreen(
                itemId = itemId,
                // 1つ前の画面に戻る
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun HomeScreen(onMoveDetail: () -> Unit) {  // home画面でボタンが押されたときに呼ぶ処理
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Home画面",
            style = MaterialTheme.typography.headlineMedium
        )

        Button(
            onClick = onMoveDetail,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Detail画面へ")
        }
    }
}

@Composable
fun DetailScreen(itemId: Int, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Detail画面",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "受け取った itemId: $itemId",
            modifier = Modifier.padding(top = 16.dp)
        )

        Button(
            onClick = onBack,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("戻る")
        }
    }
}
