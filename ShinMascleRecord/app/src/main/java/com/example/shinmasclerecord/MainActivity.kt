package com.example.shinmasclerecord

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.shinmasclerecord.data.DummyWorkoutRecords
import com.example.shinmasclerecord.navigation.AppNavHost
import com.example.shinmasclerecord.ui.theme.ShinMascleRecordTheme
import com.example.shinmasclerecord.ui.theme.page.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShinMascleRecordTheme {
                HomeScreen(
                    records = DummyWorkoutRecords.homeRecords,
                    onRecordClick = {},
                    onAddClick = {},
                )
            }
        }
    }
}
