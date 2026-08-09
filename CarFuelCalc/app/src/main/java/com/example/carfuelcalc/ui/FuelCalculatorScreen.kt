package com.example.carfuelcalc.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.testTag
import com.example.carfuelcalc.R
import com.example.carfuelcalc.domain.InputError
import com.example.carfuelcalc.domain.calculateFuelEfficiency
import com.example.carfuelcalc.domain.formatFuelEfficiency
import com.example.carfuelcalc.domain.validatePositiveNumber
import com.example.carfuelcalc.ui.theme.CarFuelCalcTheme

object FuelCalculatorTestTags {
    const val DistanceInput = "distance_input"
    const val FuelInput = "fuel_input"
    const val CalculateButton = "calculate_button"
    const val ClearButton = "clear_button"
    const val Result = "result"
}

@Composable
fun FuelCalculatorScreen(modifier: Modifier = Modifier) {
    var distanceText by remember { mutableStateOf("") }
    var fuelText by remember { mutableStateOf("") }
    var distanceError by remember { mutableStateOf<InputError?>(null) }
    var fuelError by remember { mutableStateOf<InputError?>(null) }
    var result by remember { mutableStateOf<Double?>(null) }
    var calculationFailed by remember { mutableStateOf(false) }

    val fuelFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                // title
                Text(
                    text = stringResource(R.string.screen_title),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(8.dp))
                // description
                Text(
                    text = stringResource(R.string.screen_description),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                Spacer(modifier = Modifier.height(32.dp))

                FuelInputField(
                    value = distanceText,
                    onValueChange = {
                        distanceText = it
                        distanceError = null
                        result = null
                        calculationFailed = false
                    },
                    label = stringResource(R.string.distance_label),
                    unit = stringResource(R.string.distance_unit),
                    error = distanceError,
                    imeAction = ImeAction.Next,
                    keyboardActions = KeyboardActions(
                        onNext = { fuelFocusRequester.requestFocus() },
                    ),
                    modifier = Modifier.testTag(FuelCalculatorTestTags.DistanceInput),
                )

                Spacer(modifier = Modifier.height(16.dp))

                FuelInputField(
                    value = fuelText,
                    onValueChange = {
                        fuelText = it
                        fuelError = null
                        result = null
                        calculationFailed = false
                    },
                    label = stringResource(R.string.fuel_label),
                    unit = stringResource(R.string.fuel_unit),
                    error = fuelError,
                    imeAction = ImeAction.Done,
                    keyboardActions = KeyboardActions(
                        onDone = { keyboardController?.hide() },
                    ),
                    modifier = Modifier
                        .focusRequester(fuelFocusRequester)
                        .testTag(FuelCalculatorTestTags.FuelInput),
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = {
                            distanceText = ""
                            fuelText = ""
                            distanceError = null
                            fuelError = null
                            result = null
                            calculationFailed = false
                            keyboardController?.hide()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag(FuelCalculatorTestTags.ClearButton),
                    ) {
                        Text(stringResource(R.string.clear))
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Button(
                        onClick = {
                            keyboardController?.hide()
                            val validatedDistance = validatePositiveNumber(distanceText)
                            val validatedFuel = validatePositiveNumber(fuelText)

                            distanceError = validatedDistance.error
                            fuelError = validatedFuel.error
                            result = null
                            calculationFailed = false

                            val distance = validatedDistance.value
                            val fuel = validatedFuel.value
                            if (distance != null && fuel != null) {
                                result = runCatching {
                                    calculateFuelEfficiency(distance, fuel)
                                }.getOrElse {
                                    calculationFailed = true
                                    null
                                }
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag(FuelCalculatorTestTags.CalculateButton),
                    ) {
                        Text(stringResource(R.string.calculate))
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                ResultCard(
                    result = result,
                    calculationFailed = calculationFailed,
                )
            }
        }
    }
}

@Composable
private fun FuelInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    unit: String, // 走行距離の単位
    error: InputError?,
    imeAction: ImeAction,
    keyboardActions: KeyboardActions,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        suffix = { Text(unit) },
        isError = error != null,
        supportingText = error?.let {
            { Text(inputErrorMessage(it)) }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = imeAction,
        ),
        keyboardActions = keyboardActions,
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
    )
}

@Composable
private fun inputErrorMessage(error: InputError): String = when (error) {
    InputError.Empty -> stringResource(R.string.input_error_empty)
    InputError.Invalid -> stringResource(R.string.input_error_invalid)
    InputError.NotPositive -> stringResource(R.string.input_error_not_positive)
}

@Composable
private fun ResultCard(
    result: Double?,
    calculationFailed: Boolean,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = stringResource(R.string.result_label),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = when {
                    calculationFailed -> stringResource(R.string.calculation_error)
                    result != null -> formatFuelEfficiency(result)
                    else -> stringResource(R.string.result_placeholder)
                },
                style = if (result != null) {
                    MaterialTheme.typography.headlineMedium
                } else {
                    MaterialTheme.typography.bodyLarge
                },
                fontWeight = if (result != null) FontWeight.Bold else FontWeight.Normal,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .semantics { liveRegion = LiveRegionMode.Polite }
                    .testTag(FuelCalculatorTestTags.Result),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FuelCalculatorScreenPreview() {
    CarFuelCalcTheme {
        FuelCalculatorScreen()
    }
}
