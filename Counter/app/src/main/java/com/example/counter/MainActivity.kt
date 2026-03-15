package com.example.counter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.counter.ui.theme.CounterTheme

class MainActivity : ComponentActivity() {
    // Activity が ViewModel を保持し、画面へ渡す
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CounterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        /**
                         * fillMaxWidth() と fillMaxSize() は、要素自身のサイズを広げる指定
                         * fillMaxWidth() は「親の横幅いっぱいまで広がる」
                         * fillMaxSize() は「親の横幅・高さいっぱいまで広がる」
                         * horizontalAlignment と verticalAlignment は、親レイアウトの中で子要素をどう並べるかの指定
                         */
                        MainScreen(viewModel = mainViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun CounterButtons(
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = 12.dp,
            alignment = Alignment.CenterHorizontally,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // ボタン側は状態を持たず、「押された」というイベントだけを外へ渡す
        Button(
            onClick = onIncrement,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text("+")
        }
        Button(
            onClick = onDecrement,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text("-")
        }
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel) {
    // mutableStateOf で持っている値を読むと、count が変わった時に UI が再描画される
    val count = viewModel.count

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Counter",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "${count}件")
            Spacer(modifier = Modifier.height(20.dp))

            CounterButtons(
                onIncrement = viewModel::increment,
                onDecrement = viewModel::decrement,
            )
            Spacer(modifier = Modifier.height(20.dp))

            // 画面は state を表示するだけにして、加算減算のルールは ViewModel に集約する

            // 履歴
            /* TODO */
        }
    }
}
