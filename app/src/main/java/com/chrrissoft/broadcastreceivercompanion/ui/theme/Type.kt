package com.chrrissoft.broadcastreceivercompanion.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography @Composable get() = Typography(
    titleLarge = MaterialTheme.typography.titleLarge.copy(fontWeight = Medium)
)
