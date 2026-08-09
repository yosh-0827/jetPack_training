package com.example.carfuelcalc.ui

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.text.AnnotatedString
import com.example.carfuelcalc.MainActivity
import org.junit.Rule
import org.junit.Test

class FuelCalculatorScreenTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun validInputs_showCalculatedResult() {
        composeRule.onNodeWithTag(FuelCalculatorTestTags.DistanceInput)
            .performTextInput("500")
        composeRule.onNodeWithTag(FuelCalculatorTestTags.FuelInput)
            .performTextInput("40")

        composeRule.onNodeWithTag(FuelCalculatorTestTags.CalculateButton).performClick()

        composeRule.onNodeWithText("12.50 km/L").assertIsDisplayed()
    }

    @Test
    fun emptyInputs_showErrorsForBothFields() {
        composeRule.onNodeWithTag(FuelCalculatorTestTags.CalculateButton).performClick()

        composeRule.onAllNodesWithText("入力してください").assertCountEquals(2)
    }

    @Test
    fun changingInput_clearsOldResult() {
        composeRule.onNodeWithTag(FuelCalculatorTestTags.DistanceInput)
            .performTextInput("500")
        composeRule.onNodeWithTag(FuelCalculatorTestTags.FuelInput)
            .performTextInput("40")
        composeRule.onNodeWithTag(FuelCalculatorTestTags.CalculateButton).performClick()
        composeRule.onNodeWithText("12.50 km/L").assertIsDisplayed()

        composeRule.onNodeWithTag(FuelCalculatorTestTags.DistanceInput)
            .performTextClearance()

        composeRule.onNodeWithText("12.50 km/L").assertDoesNotExist()
    }

    @Test
    fun clearButton_resetsInputsAndResult() {
        composeRule.onNodeWithTag(FuelCalculatorTestTags.DistanceInput)
            .performTextInput("500")
        composeRule.onNodeWithTag(FuelCalculatorTestTags.FuelInput)
            .performTextInput("40")
        composeRule.onNodeWithTag(FuelCalculatorTestTags.CalculateButton).performClick()

        composeRule.onNodeWithTag(FuelCalculatorTestTags.ClearButton).performClick()

        val emptyInput = SemanticsMatcher.expectValue(
            SemanticsProperties.EditableText,
            AnnotatedString(""),
        )
        composeRule.onNodeWithTag(FuelCalculatorTestTags.DistanceInput).assert(emptyInput)
        composeRule.onNodeWithTag(FuelCalculatorTestTags.FuelInput).assert(emptyInput)
        composeRule.onNodeWithText("12.50 km/L").assertDoesNotExist()
    }

    @Test
    fun activityRecreation_doesNotSaveInputsOrResult() {
        composeRule.onNodeWithTag(FuelCalculatorTestTags.DistanceInput)
            .performTextInput("500")
        composeRule.onNodeWithTag(FuelCalculatorTestTags.FuelInput)
            .performTextInput("40")
        composeRule.onNodeWithTag(FuelCalculatorTestTags.CalculateButton).performClick()

        composeRule.activityRule.scenario.recreate()

        val emptyInput = SemanticsMatcher.expectValue(
            SemanticsProperties.EditableText,
            AnnotatedString(""),
        )
        composeRule.onNodeWithTag(FuelCalculatorTestTags.DistanceInput).assert(emptyInput)
        composeRule.onNodeWithTag(FuelCalculatorTestTags.FuelInput).assert(emptyInput)
        composeRule.onNodeWithText("12.50 km/L").assertDoesNotExist()
    }
}
