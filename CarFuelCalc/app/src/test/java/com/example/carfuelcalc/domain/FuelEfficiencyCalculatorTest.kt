package com.example.carfuelcalc.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class FuelEfficiencyCalculatorTest {
    @Test
    fun calculateFuelEfficiency_500kmAnd40Liters_returns12Point5() {
        val result = calculateFuelEfficiency(distanceKm = 500.0, fuelLiters = 40.0)

        assertEquals(12.5, result, 0.0)
        assertEquals("12.50 km/L", formatFuelEfficiency(result))
    }

    @Test
    fun calculateFuelEfficiency_decimalInputs_returnsExpectedValue() {
        val result = calculateFuelEfficiency(distanceKm = 123.4, fuelLiters = 10.0)

        assertEquals(12.34, result, 0.000001)
        assertEquals("12.34 km/L", formatFuelEfficiency(result))
    }

    @Test
    fun formatFuelEfficiency_roundsToTwoDecimalPlaces() {
        assertEquals("3.33 km/L", formatFuelEfficiency(10.0 / 3.0))
    }

    @Test
    fun validatePositiveNumber_emptyInput_returnsEmptyError() {
        assertEquals(InputError.Empty, validatePositiveNumber(" ").error)
    }

    @Test
    fun validatePositiveNumber_invalidDecimal_returnsInvalidError() {
        assertEquals(InputError.Invalid, validatePositiveNumber("12.3.4").error)
    }

    @Test
    fun validatePositiveNumber_nonFiniteInput_returnsInvalidError() {
        assertEquals(InputError.Invalid, validatePositiveNumber("Infinity").error)
        assertEquals(InputError.Invalid, validatePositiveNumber("NaN").error)
    }

    @Test
    fun validatePositiveNumber_zeroOrNegative_returnsNotPositiveError() {
        assertEquals(InputError.NotPositive, validatePositiveNumber("0").error)
        assertEquals(InputError.NotPositive, validatePositiveNumber("-1").error)
    }

    @Test
    fun calculateFuelEfficiency_invalidArguments_throwException() {
        assertThrows(IllegalArgumentException::class.java) {
            calculateFuelEfficiency(distanceKm = 0.0, fuelLiters = 10.0)
        }
        assertThrows(IllegalArgumentException::class.java) {
            calculateFuelEfficiency(distanceKm = 100.0, fuelLiters = Double.POSITIVE_INFINITY)
        }
    }
}
