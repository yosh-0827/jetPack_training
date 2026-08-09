package com.example.carfuelcalc.domain

import java.util.Locale

enum class InputError {
    Empty,
    Invalid,
    NotPositive,
}

data class ValidatedNumber(
    val value: Double? = null,
    val error: InputError? = null,
)

/**
 * 文字列を、0より大きい有限の数値として検証します。
 */
fun validatePositiveNumber(input: String): ValidatedNumber {
    if (input.isBlank()) {
        return ValidatedNumber(error = InputError.Empty)
    }

    val number = input.trim().toDoubleOrNull()
        ?: return ValidatedNumber(error = InputError.Invalid)

    if (!number.isFinite()) {
        return ValidatedNumber(error = InputError.Invalid)
    }

    if (number <= 0.0) {
        return ValidatedNumber(error = InputError.NotPositive)
    }

    return ValidatedNumber(value = number)
}

/**
 * 満タン法（走行距離 ÷ 給油量）で燃費を計算します。
 */
fun calculateFuelEfficiency(distanceKm: Double, fuelLiters: Double): Double {
    require(distanceKm.isFinite() && distanceKm > 0.0) {
        "distanceKm must be a positive finite number"
    }
    require(fuelLiters.isFinite() && fuelLiters > 0.0) {
        "fuelLiters must be a positive finite number"
    }

    val fuelEfficiency = distanceKm / fuelLiters
    require(fuelEfficiency.isFinite()) {
        "The calculated fuel efficiency must be finite"
    }
    return fuelEfficiency
}

/**
 * 燃費を小数第2位までの画面表示用文字列に変換します。
 */
fun formatFuelEfficiency(fuelEfficiency: Double): String {
    require(fuelEfficiency.isFinite() && fuelEfficiency > 0.0) {
        "fuelEfficiency must be a positive finite number"
    }
    return String.format(Locale.JAPAN, "%.2f km/L", fuelEfficiency)
}
