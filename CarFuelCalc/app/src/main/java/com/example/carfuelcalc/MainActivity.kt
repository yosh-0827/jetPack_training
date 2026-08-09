package com.example.carfuelcalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.carfuelcalc.ui.FuelCalculatorScreen
import com.example.carfuelcalc.ui.theme.CarFuelCalcTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CarFuelCalcTheme {
                FuelCalculatorScreen()
            }
        }
    }
}
