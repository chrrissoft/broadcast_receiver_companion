package com.chrrissoft.broadcastreceivercompanion.ui.theme

import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
val inputChipColors
    @Composable get() = InputChipDefaults.inputChipColors(
        containerColor = colorScheme.tertiaryContainer,
        labelColor = colorScheme.tertiary,
        leadingIconColor = colorScheme.tertiary,
        trailingIconColor = colorScheme.tertiary,
        disabledContainerColor = colorScheme.tertiaryContainer.copy(.5f),
        disabledLabelColor = colorScheme.tertiary.copy(.5f),
        disabledLeadingIconColor = colorScheme.tertiary.copy(.5f),
        disabledTrailingIconColor = colorScheme.tertiary.copy(.5f),
        selectedContainerColor = colorScheme.tertiary,
        disabledSelectedContainerColor = colorScheme.tertiary.copy(.5f),
        selectedLabelColor = colorScheme.onTertiary,
        selectedLeadingIconColor = colorScheme.onTertiary,
        selectedTrailingIconColor = colorScheme.onTertiary,
    )

@OptIn(ExperimentalMaterial3Api::class)
val inputChipBorderColor
    @Composable get() = InputChipDefaults.inputChipBorder(
        borderColor = colorScheme.tertiary
    )

val checkboxColors
    @Composable get() = CheckboxDefaults.colors(
        checkedColor = colorScheme.tertiary,
        uncheckedColor = colorScheme.secondary,
        checkmarkColor = colorScheme.onTertiary,
        disabledCheckedColor = colorScheme.tertiary.copy(.5f),
        disabledUncheckedColor = Color.Transparent,
        disabledIndeterminateColor = Color.Red,
    )

val cardColors
    @Composable get() = CardDefaults.cardColors(
        containerColor = colorScheme.tertiaryContainer,
    )

val radioColors
    @Composable get() = RadioButtonDefaults.colors(
        selectedColor = colorScheme.tertiary,
        unselectedColor = colorScheme.secondary,
        disabledSelectedColor = colorScheme.tertiary.copy(.5f),
        disabledUnselectedColor = colorScheme.secondary.copy(.5f),
    )