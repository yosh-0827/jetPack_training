package com.example.masclerecord

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.masclerecord.navigation.AppNavHost
import com.example.masclerecord.ui.theme.MascleRecordTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MascleRecordTheme {
                AppNavHost()
            }
        }
    }
}
